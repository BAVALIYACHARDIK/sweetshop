export const registeruser = async ({name,email,password})=>{
    const reponse = await fetch('/api/auth/register',{
        method:'post',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({name,email,password})
    });
    return Response.json();
    
}