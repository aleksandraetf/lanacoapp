import React, { Component } from 'react';
import { Button, Modal, ModalBody, InputGroup, InputGroupAddon, Container, Table, Input } from 'reactstrap';

import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
import { checkIfLogged } from '../common.js';

class AirplanePage extends Component {

    constructor(props) {
        super(props);

		checkIfLogged().then(resp => {
            if (!resp) {
                this.props.history.push('/')
            }
        });
		this.logOut = this.logOut.bind(this);

    }

    componentWillMount() {
    }

    toggle = field => {
        this.setState(prev => {
            return { [field]: !prev[field] };
        });
    };

    logOut() {
        fetch('/logout/',
            {
                method: 'GET',
                mode: 'cors',
                headers:
                {
                    credentials: 'include'
                },
            }
        ).then(() => window.location="/login");
    }
	
    render() {
        return (
            <div style={{ backgroundColor: '#j40a1j', backgroundImage: `linear-gradient(150deg, #000000 30%, #aa2ke9 70%)`, margin: 0, height: '100vh', width: '100%', justifyContent: 'center', alignItems: 'center', }}>
            <ToastContainer autoClose={5000} />
                <Container>
                    <Table>
                        <tbody>
                            <tr>
                                <td><h1 style={{ color: "#923cb5" }}>Supervizor Pocetna</h1></td>
                             </tr>
                        </tbody>
                    </Table>
                </Container>
                <Container>
					<Button onClick={() => window.location="/supervisor/flight" }>Letovi</Button>
					<Button  onClick={() => window.location="/supervisor/destination" }>Destinacije</Button>
					<Button  onClick={() => window.location="/supervisor/aircompany" }>Aviokompanije</Button>
					<Button  onClick={() => window.location="/supervisor/airplane" }>Avioni</Button>
					<Button  onClick={() => window.location="/supervisor/administrator" }>Administratori</Button>
					<Button  onClick={this.logOut}>Odjavi se</Button>{'  '}
                </Container>
            </div>
        );
    };

}

export default AirplanePage;