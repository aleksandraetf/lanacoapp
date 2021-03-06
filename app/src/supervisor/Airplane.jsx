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
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleInputChangeSeats = this.handleInputChangeSeats.bind(this);
		this.handleDelete=this.handleDelete.bind(this);


        this.state = { airplanes : [], showModal: false, message: "", brand: "" , seats: 0}

    }

    loadData() {
        fetch('/api/airplane/')
            .then(response => response.json())
            .then(data => this.setState({ airplanes: data }));
    }

    componentWillMount() {
        this.loadData();
    }

    cleanData() {
        this.setState({ message: "", brand: "", seats: 0 });
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


    handleInputChange(event) {
        this.setState({ [event.target.name]: event.target.value});
    }

    handleInputChangeSeats(event) {
        this.setState({ [event.target.name]: event.target.value });
    }
	
	handleDelete(event){
		let dataToSend = {
            brand: event.target.value
        }
        fetch('/api/airplane/',
            {
                method: 'DELETE',
                headers:
                {

                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    credentials: 'include'
                },
                mode:'cors',
                credentials:'include',
                body: JSON.stringify(dataToSend),
            }
        ).then(response => { if (response.status === 202) { this.loadData(); this.cleanData(); toast.success("Avion obrisan!", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Aircompany not saved! Fields can not be empty and it is not possible to add existing Aircompany!"}) } });
	}

    handleSubmit(event) {
        let dataToSend = {
            brand: this.state.brand,
            seats: this.state.seats
        }

        fetch('/api/airplane/',
            {
                method: 'POST',
                headers:
                {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    credentials: 'include'
                },
                mode:'cors',
                //credentials:'include',
                body: JSON.stringify(dataToSend),
            }
        ).then(response => {if (response.status === 202) { this.loadData(); this.cleanData(); this.toggle('showModal'); toast.success("Avion sacuvan", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Avion nije sacuvan." }) } });
    }

    render() {
        let airplanes = [...this.state.airplanes];
        return (
           
            <div style={{ backgroundColor: '#j40a1j', backgroundImage: `linear-gradient(150deg, #000000 30%, #aa2ke9 70%)`, margin: 0, height: '100vh', width: '100%', justifyContent: 'center', alignItems: 'center', }}>
            <ToastContainer autoClose={5000} />
                <Container>
                    <Modal
                        isOpen={this.state.showModal}
                        toggle={() => this.toggle('showModal')}
                        className="bg-transparent modal-xl"

                    >
                        <ModalBody>
                            <div>
                                <InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Brend aviona:
                                    </InputGroupAddon>
                                    <Input
                                        type="text" name="brand" id="brand" value={this.state.brand} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>

                                <InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Broj sjedista:
                                    </InputGroupAddon>
                                    <Input
                                        type="number" name="seats" id="seats" value={this.state.seats} onChange={this.handleInputChangeSeats}
                                    ></Input>
                                </InputGroup>
                              
                                <p style={{ color: '#923cb5' }}>{this.state.message}</p>
                                <br></br>
                                <Button style={{ backgroundColor: "#923cb5" }} onClick={this.handleSubmit}>Dodaj avion</Button>
                            </div>
                        </ModalBody>
                    </Modal>
                </Container>
                <Container>
                    <Table>
                        <tbody>
                            <tr>
                                <td><h1 style={{ color: "#923cb5" }}>Avioni</h1></td>
                             </tr>
                        </tbody>
                    </Table>
                </Container>
                <Container>
                <Button onClick={this.logOut}>Odjavi se</Button>{'  '}
                 <br></br><br></br>
                    <Button  onClick={() => this.toggle('showModal')}>Dodaj novi avion</Button><br></br><br></br>
					<Button  onClick={() => window.location="/supervisor" }>Vrati se na pocetnu</Button>
				   <Table striped bordered hover>       
                   <thead>
                            <tr><th>Brend</th><th>Broj sjedista</th></tr>
                        </thead>
                        <tbody>
                            
                            {
                                airplanes.map((airplane) => {
                                    return <tr key={airplane.id}><td>{airplane.brand}</td><td>{airplane.seats}</td>
										<td>
												<Button style={{  }} value={airplane.brand} onClick={this.handleDelete}>Obrisi</Button>
										</td>
									</tr>
                                })
                            }
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    };

}

export default AirplanePage;