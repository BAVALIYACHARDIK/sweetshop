import React from 'react';
import { useState } from 'react';
import { registeruser } from '../services/api'; 

function Register() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [status, setStatus] = useState(null);

    const submit = async (e) => {
        e.preventDefault();
        setStatus('loading');
        try {
            const response = await registeruser({ name, email, password });
            if (response && response.message === 'Email already in use') {
                setStatus({ ok: false, error: response.message });
            } else {
                setStatus({ ok: true, data: response });
            }
            console.log('register response', response);
        } catch (err) {
            setStatus({ ok: false, error: err.message || err });
            console.error(err);
        }
    };

    return (
        <div>
            <h2>Register</h2>
            <form onSubmit={submit}>
                <div>
                    <label>Name:</label>
                    <input 
                        type="text" 
                        value={name} 
                        onChange={(e)=>setName(e.target.value)} 
                        required 
                    />
                </div>

                <div>
                    <label>Email:</label>
                    <input 
                        type="email" 
                        value={email} 
                        onChange={(e)=>setEmail(e.target.value)} 
                        required 
                    />
                </div>

                <div>
                    <label>Password:</label>
                    <input 
                        type="password" 
                        value={password} 
                        onChange={(e)=>setPassword(e.target.value)} 
                        required 
                    />
                </div>
                <button type="submit">Register</button>
            </form>

            {status === 'loading' && <p>Registering…</p>}
            {status && status.ok && <p>Registered successfully</p>}
            {status && status.ok === false && <p style={{ color: 'red' }}>{status.error}</p>}
        </div>
    );
}

export default Register;
