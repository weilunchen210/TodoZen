import { useState } from 'react'
import "./RegisterForm.css"
import { registerUser } from '../../services/userService'
import { useNavigate } from 'react-router'


function  RegisterForm() {

    const [username,setUsername] = useState("")
    const [password,setPassword] = useState("")
    const [email, setEmail] = useState("")
    const [profilePictureURL, setprofilePictureURL] = useState("")
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const newUser = {
            username: username,
            password: password,
            email: email,
            profilePictureURL:profilePictureURL
        }
        try {
            await registerUser(newUser);
            navigate('/login'); // Use navigate function here
        } catch (error) {
            console.error('Registration failed:', error);
        }
    }

    return (
        <div className="register-container-wrapper">
        <div className="register-box">
            <div className="register-title">
                <label className ="title">
                    Register
                </label>
            </div>
            <form onSubmit={handleSubmit}>
                <div className="input">
                        <input type="email" 
                            value={email} 
                            onChange={(e) => setEmail(e.target.value)} 
                            placeholder="Email" 
                            required></input>
                </div>
                <div className="input">
                    <input type="text" 
                        value={username} onChange={(e) => setUsername(e.target.value)} 
                        placeholder="Username" 
                        required></input>
                </div>
                <div className="input">
                    <input type="password" 
                        value={password} onChange={(e) => setPassword(e.target.value)} 
                        placeholder="Password" 
                        required></input>
                </div>
                <div className="input">
                    <input type="text" 
                        value={profilePictureURL} onChange={(e) => setprofilePictureURL(e.target.value)} 
                        placeholder="Profile Picture URL" 
                        required></input>
                </div>
                <div className="submit">
                    <input className="form-button" type="submit" value="Register"></input>
                </div>
            </form>
        </div>
        </div>
    )
}

export default RegisterForm
