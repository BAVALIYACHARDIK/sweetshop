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