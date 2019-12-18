import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Button, InputGroup, InputGroupAddon, Container, Input } from 'reactstrap';
import { checkIfLogged } from './common.js'

class Login extends Component {
  
  handleInputChange(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  constructor(props) {
    super(props);
	
    checkIfLogged().then(resp => {
      if (resp) {
        this.props.history.push('/aircompany')
      }
    });
	this.register=this.register.bind(this);
    this.handleInputChange = this.handleInputChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);


    this.state = {role: "", registerEmail:"",registerPassword:"", email: "", password: "", message: "" };
  }
  
  register() {
	  let dataToSend = {
            email: this.state.registerEmail,
			password: this.state.registerPassword
        }
	  fetch('/register/',
      {
        method: 'POST',
        headers:
        {

          'Accept': 'application/json',
          'Content-Type': 'application/json',
          credentials: 'include'
        },
        body: JSON.stringify(dataToSend),
      }
    ).then(response => { 
		if (response.status === 200) {
			if(this.refs.roleRef.value==="USER"){
				this.props.history.push('/user');
			}
			else if(this.refs.roleRef.value==="ADMINISTRATOR"){
				this.props.history.push('/administrator');
			}
			else if(this.refs.roleRef.value==="SUPERVISOR")
				this.props.history.push('/supervisor');
			}
		else { 
			this.setState({ message: "Invalid credentials"+response.status+response }) 
		}
		});
  }

  handleSubmit() {
	  this.state.role=this.refs.roleRef.value;
	  let dataToSend = {
            email: this.state.email,
			password: this.state.password,
			role: this.state.role
        }
		console.log(dataToSend);
		let url='';
		if(this.refs.roleRef.value==="USER"){
			url='/login/user/';
		}
		else if(this.refs.roleRef.value==="ADMINISTRATOR"){
			url='/login/administrator/';
		}
		else if(this.refs.roleRef.value==="SUPERVISOR")
			url='/login/supervisor/';
    fetch(url,
      {
        method: 'POST',
        headers:
        {

          'Accept': 'application/json',
          'Content-Type': 'application/json',
          credentials: 'include'
        },
        body: JSON.stringify(dataToSend),
      }
    ).then(response => { 
	if (response.status === 200) { 
		if(this.refs.roleRef.value==="USER"){
			this.props.history.push('/user');
		}
		else if(this.refs.roleRef.value==="ADMINISTRATOR"){
			this.props.history.push('/administrator');
		}
		else if(this.refs.roleRef.value==="SUPERVISOR")
			this.props.history.push('/supervisor');
	} 
	else { this.setState({ message: "Invalid credentials"+response.status+response }) } });
  }

  render() {
    return (
      <div style={{ backgroundColor: '#923cb5', backgroundImage: `linear-gradient(150deg, #000000 0%, #923cb5 70%)`, margin: 0, height: '100vh', width: '100%', justifyContent: 'center', alignItems: 'center', }}>
        <h1 style={{ color: "#923cb5" }}>Login page</h1>
        <Container >
          <div>
            <InputGroup size="sm">
              <InputGroupAddon sm={3} addonType="prepend" className=" bg-dark">
                Mail:
              </InputGroupAddon>
              <Input style={{ backgroundColor: '#923cb5', backgroundImage: `linear-gradient(50deg, #923cb5 0%, #000000 5%)`, color: "#923cb5" }}
                type="text" name="email" id="email" value={this.state.email} onChange={this.handleInputChange} onBlur={this.handleMailInputChange}>

              </Input>
            </InputGroup>
            <InputGroup size="sm">
              <InputGroupAddon addonType="prepend" style={{ backgroundColor: "#923cb5" }}>
                Password:
              </InputGroupAddon>
              <Input className="bg-dark text-success" style={{ backgroundColor: '#923cb5', backgroundImage: `linear-gradient(50deg, #000000 0%, #923cb5 99%)` }}
                type="password" name="password" id="password" value={this.state.password} onChange={(event) => this.handleInputChange(event)} onBlur={this.handlePassInputChange}>
              </Input>
            </InputGroup>
            <p style={{ color: '#923cb5' }}>{this.state.message}</p>
            <br></br>
			<select ref="roleRef" name="custom-search-select"
									className="custom-search-select">
				  <option value="SUPERVISOR">Supervisor</option>
				  <option value="ADMINISTRATOR">Administrator</option>
				  <option selected value="USER">User</option>
			</select>
            <Button style={{ backgroundColor: "#923cb5" }} onClick={this.handleSubmit}>Log In</Button>{'  '}
			
			
			
			
			
			<InputGroup size="sm">
              <InputGroupAddon sm={3} addonType="prepend" className=" bg-dark">
                Mail:
              </InputGroupAddon>
              <Input style={{ backgroundColor: '#923cb5', backgroundImage: `linear-gradient(50deg, #923cb5 0%, #000000 5%)`, color: "#923cb5" }}
                type="text" name="registerEmail" id="registerEmail" value={this.state.registerEmail} onChange={this.handleInputChange} onBlur={this.handleMailInputChange}>

              </Input>
            </InputGroup>
            <InputGroup size="sm">
              <InputGroupAddon addonType="prepend" style={{ backgroundColor: "#923cb5" }}>
                Password:
              </InputGroupAddon>
              <Input className="bg-dark text-success" style={{ backgroundColor: '#923cb5', backgroundImage: `linear-gradient(50deg, #000000 0%, #923cb5 99%)` }}
                type="password" name="registerPassword" id="registerPassword" value={this.state.registerPassword} onChange={(event) => this.handleInputChange(event)} onBlur={this.handlePassInputChange}>
              </Input>
            </InputGroup>
            <p style={{ color: '#923cb5' }}>{this.state.message}</p>
            <br></br>
            <Button style={{ backgroundColor: "#923cb5" }} onClick={this.register}>Register</Button>{'  '}
            <Link className="btn btn-outline-danger" to="/">Cancel</Link>
          </div>
        </Container>
      </div>
    );
  };

}

export default Login;