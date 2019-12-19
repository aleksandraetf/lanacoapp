import React, { Component } from 'react';
import { Button, Modal, ModalBody, InputGroup, InputGroupAddon, Container, Table, Input } from 'reactstrap';

import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
import { checkIfLogged } from '../common.js';

class AircompanyPage extends Component {

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


        this.state = { aircompanies: [], showModal: false, message: "", name: "" }

    }

    loadData() {
        fetch('/api/aircompany/')
            .then(response => response.json())
            .then(data => this.setState({ aircompanies: data }));
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
        fetch('/api/aircompany/',
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
        ).then(response => { if (response.status === 202) { this.loadData(); this.cleanData(); this.toggle('showModal'); toast.success("Aircompany Saved", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Aircompany not saved! Fields can not be empty and it is not possible to add existing Aircompany!"}) } });
	}

    handleSubmit(event) {
        let dataToSend = {
            name: this.state.name
        }
        fetch('/api/aircompany/',
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
        ).then(response => { if (response.status === 202) { this.loadData(); this.cleanData(); this.toggle('showModal'); toast.success("Aircompany Saved", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Aircompany not saved! Fields can not be empty and it is not possible to add existing Aircompany!"}) } });
    }

    render() {
        console.log("RENDER:")
        console.log(this.state);
        let aircompanies = [...this.state.aircompanies];
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
                                        Ime avio kompanije:
                                    </InputGroupAddon>
                                    <Input
                                        type="text" name="name" id="name" value={this.state.name} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>
                              
                                <p style={{ color: '#923cb5' }}>{this.state.message}</p>
                                <br></br>
                                <Button style={{ backgroundColor: "#923cb5" }} onClick={this.handleSubmit}>Dodaj avio kompaniju</Button>
                            </div>
                        </ModalBody>
                    </Modal>
                </Container>
                <Container>
                    <Table>
                        <tbody>
                            <tr>
                                <td><h1 style={{ color: "#923cb5" }}>Aircompany Page</h1></td>
                             </tr>
                        </tbody>
                    </Table>
                </Container>
                <Container>
                <Button  onClick={this.logOut}>Log Out</Button>{'  '}
                <br></br><br></br>
                    <Button onClick={() => this.toggle('showModal')}>Dodaj novu avio kompaniju</Button><br></br><br></br>
					<Button  onClick={() => window.location="/supervisor/flight" }>Letovi</Button>
					<Button  onClick={() => window.location="/supervisor/destination" }>Destinacije</Button>
					<Button onClick={() => window.location="/supervisor/aircompany" }>Avio kompanije</Button>
					<Button  onClick={() => window.location="/supervisor/airplane" }>Avioni</Button>
					<Button  onClick={this.logOut}>Log Out</Button>{'  '}
                    <Table striped bordered hover>
                    <thead>
                            <tr><th>Ime</th><th></th></tr>
                        </thead>
                        <tbody>
                            {
                                aircompanies.map((aircompany) => {
                                    return <tr key={aircompany.id}><td>{aircompany.name}</td><td>
											<Button value={aircompany.name} style={{ }} onClick={this.handleDelete}>Obisi</Button>
										</td></tr>
                                })
                            }
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    };

}

export default AircompanyPage;