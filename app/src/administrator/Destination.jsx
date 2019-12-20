import React, { Component } from 'react';
import { Button, Modal, ModalBody, InputGroup, InputGroupAddon, Container, Table, Input } from 'reactstrap';

import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';

import { checkIfLogged } from '../common.js';

class DestinationPage extends Component {

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
		this.handleDelete=this.handleDelete.bind(this);


        this.state = { destinations: [], showModal: false, message: "", name: "" }

    }

    loadData() {
        fetch('/api/destination/')
            .then(response => response.json())
            .then(data => this.setState({ destinations: data }));
    }

    componentWillMount() {
        this.loadData();
    }

    cleanData() {
        this.setState({ message: "", name: "" });
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
        this.setState({ [event.target.name]: event.target.value });
    }
	
	handleDelete(event){
		console.log(event.target.value);
		let dataToSend = {
            name: event.target.value
        }
        fetch('/api/destination/',
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
        ).then(response => { if (response.status === 202) { this.loadData(); this.cleanData(); toast.success("Destinacija obrisana!", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Aircompany not saved! Fields can not be empty and it is not possible to add existing Aircompany!"}) } });
	}


    handleSubmit(event) {
        let dataToSend = {
            name: this.state.name
        }

        fetch('/api/destination/',
            {
                method: 'POST',
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
        ).then(response => { if (response.status === 202) { this.loadData(); this.cleanData(); this.toggle('showModal'); toast.success("Destinacija je sacuvana.", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Destinacija nije sacuvana! Polja ne mogu biti prazna. Ne mozete dodati vec postojecu destinaciju!"}) } });
    }

    render() {
        console.log("RENDER:")
        console.log(this.state);
        let destinations = [...this.state.destinations];
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
                                       Ime destinacije:
                                    </InputGroupAddon>
                                    <Input
                                        type="text" name="name" id="name" value={this.state.name} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>
                              
                                <p style={{ color: '#923cb5' }}>{this.state.message}</p>
                                <br></br>
                                <Button style={{ backgroundColor: "#923cb5" }} onClick={this.handleSubmit}>Dodaj novu destinaciju</Button>
                            </div>
                        </ModalBody>
                    </Modal>
                </Container>
                <Container>
                    <Table>
                        <tbody>
                            <tr>
                                <td><h1 style={{ color: "#923cb5" }}>Destinacije</h1></td>
                             </tr>
                        </tbody>
                    </Table>
                </Container>
                <Container>
                    <Button  onClick={() => this.toggle('showModal')}>Dodaj novu destinaciju</Button><br></br><br></br>
					<Button  onClick={() => window.location="/administrator" }>Vrati se na pocetnu stranu</Button>
					<Button onClick={this.logOut}>Log Out</Button>
                    <Table striped bordered hover >
                        <thead>
                            <tr><th>Ime destinacije</th></tr>
                        </thead>
                        <tbody>
                            {
                                destinations.map((destination) => {
                                    return <tr key={destination.id}><td>{destination.name}</td>
									
									<td>
										<Button value={destination.name} style={{ backgroundColor: "#923cb5" }} onClick={this.handleDelete}>Obrisi</Button>
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

export default DestinationPage;