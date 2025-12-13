import { usestate } from 'react';
import { registeruser } from '../services/api'; 

function Register() {
    const [name,setname]=usestate('');
    const [email,setemail]=usestate('');
    const [password,setpassword]=usestate('');

    const submit = async(e)=>{
        e.preventDefault();
        const response=await registeruser({name,email,password});
        console.log(response);
        
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
                        onChange={(e)=>setname(e.target.value)} 
                        required 
                    />
                </div>

                <div>
                    <label>Email:</label>
                    <input 
                        type="email" 
                        value={email} 
                        onChange={(e)=>setemail(e.target.value)} 
                        required 
                    />
                </div>

                <div>
                    <label>Password:</label>
                    <input 
                        type="password" 
                        value={password} 
                        onChange={(e)=>setpassword(e.target.value)} 
                        required 
                    />
                </div>
                <button type="submit">Register</button>
            </form>
        </div>
    );
}

export default Register;
