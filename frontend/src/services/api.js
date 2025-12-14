// Check whether a stored token exists and (if it's a JWT) is not expired.
function _parseJwtPayload(token) {
    try {
        const parts = token.split('.');
        if (parts.length !== 3) return null;
        const payload = parts[1];
        
        const base64 = payload.replace(/-/g, '+').replace(/_/g, '/');
        const pad = base64.length % 4;
        const padded = base64 + (pad ? '='.repeat(4 - pad) : '');
        const json = atob(padded);
        return JSON.parse(json);
    } catch (e) {
        return null;
    }
}

export const isTokenValid = () => {
    try {
        const token = localStorage.getItem('token');
        if (!token) return false;

        const payload = _parseJwtPayload(token);
        
        if (!payload) return true;

        if (typeof payload.exp !== 'number') return true;
        const now = Math.floor(Date.now() / 1000);
        return payload.exp > now;
    } catch (e) {
        return false;
    }
};


export const registeruser = async ({name,email,password})=>{
    const response = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ name, email, password })
    });
    
    if (!response.ok) {
        const text = await response.text().catch(() => null);
        throw new Error(text || `Request failed with status ${response.status}`);
    }

    return response.json();
}

export const loginuser = async ({email,password}) => {
    const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ email, password })
    });

    if (!response.ok) {
        const text = await response.text().catch(() => null);
        throw new Error(text || `Request failed with status ${response.status}`);
    }

    return response.json();
}