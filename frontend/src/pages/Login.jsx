import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginuser, isTokenValid } from '../services/api';

function Login() {
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');
	const [status, setStatus] = useState(null);
	const navigate = useNavigate();

	// useEffect(() => {
	// 	try {
	// 		if (isTokenValid()) {
	// 			navigate('/sweet');
	// 		}
	// 	} catch (e) {
	// 		console.error('Token validation failed', e);
	// 	}
	// }, [navigate]);

	const submit = async (e) => {
		e.preventDefault();
		setStatus('loading');
		try {
			const response = await loginuser({ email, password });
			if (response && response.message) {
				setStatus({ ok: false, error: response.message });
			} else {
				try {
					if (response && response.token) {
						localStorage.setItem('token', response.token);
					}
					localStorage.setItem('user', JSON.stringify(response));
				} catch (storageErr) {
					console.error('Failed to write to localStorage', storageErr);
				}
				setStatus({ ok: true, data: response });
				navigate('/');
			}
			console.log('login response', response);
            if(response){
                navigate('/sweet');
            }
		} catch (err) {
			setStatus({ ok: false, error: err.message || err });
			console.error(err);
		}
	};

	return (
		<div className="auth-container">
			<h2>Login</h2>
			<div className="auth-redirect">
				<span>Don't have an account?</span>
				<button type="button" className="link-btn" onClick={() => navigate('/')}>Go to Register</button>
			</div>
			<form className="auth-form" onSubmit={submit}>
				<div className="form-group">
					<label>Email:</label>
					<input 
						type="email" 
						value={email} 
						onChange={(e)=>setEmail(e.target.value)} 
						required 
					/>
				</div>

				<div className="form-group">
					<label>Password:</label>
					<input 
						type="password" 
						value={password} 
						onChange={(e)=>setPassword(e.target.value)} 
						required 
					/>
				</div>

				<div className="form-actions">
					<button type="submit" className="btn-primary">Login</button>
				</div>
			</form>

			{status === 'loading' && <p className="status-message">Logging in…</p>}
			{status && status.ok && <p className="status-message success">Logged in successfully</p>}
			{status && status.ok === false && <p className="status-message error">{status.error}</p>}
		</div>
	);
}

export default Login;
