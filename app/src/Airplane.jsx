import React, { Component } from 'react';
import { Button, Modal, ModalBody, InputGroup, InputGroupAddon, Container, Table, Input } from 'reactstrap';

import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';

class AirplanePage extends Component {

    constructor(props) {
        super(props);

      /*  checkIfLogged().then(resp => {
            if (!resp) {
                this.props.history.push('/')
            }
        });
*/
      //  this.logOut = this.logOut.bind(this);
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

  /*  logOut() {
        fetch('/auth/logout',
            {
                method: 'GET',
                mode: 'cors',
                headers:
                {
                    credentials: 'include'
                },
            }
        ).catch(() => this.props.history.push('/'));
    }
*/


    handleInputChange(event) {
        this.setState({ [event.target.name]: event.target.value});
    }

    handleInputChangeSeats(event) {
        this.setState({ [event.target.name]: event.target.value });
    }
	
	handleDelete(event){
		console.log(event.target.value);
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
        ).then(response => { if (response.status === 202) { this.loadData(); this.cleanData(); this.toggle('showModal'); toast.success("Aircompany Saved", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Aircompany not saved! Fields can not be empty and it is not possible to add existing Aircompany!"}) } });
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
        ).then(response => {if (response.status === 202) { this.loadData(); this.cleanData(); this.toggle('showModal'); toast.success("Airplane Saved", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Airplane not saved! Fields can not be empty and it is not possible to add existing Airplane!" }) } });
    }

    render() {
        console.log(this.state);
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
                                        Airplane Brand:
                                    </InputGroupAddon>
                                    <Input
                                        type="text" name="brand" id="brand" value={this.state.brand} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>

                                <InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Airplane Seats:
                                    </InputGroupAddon>
                                    <Input
                                        type="number" name="seats" id="seats" value={this.state.seats} onChange={this.handleInputChangeSeats}
                                    ></Input>
                                </InputGroup>
                              
                                <p style={{ color: '#923cb5' }}>{this.state.message}</p>
                                <br></br>
                                <Button style={{ backgroundColor: "#923cb5" }} onClick={this.handleSubmit}>Add Airplane</Button>
                            </div>
                        </ModalBody>
                    </Modal>
                </Container>
                <Container>
                    <Table>
                        <tbody>
                            <tr>
                                <td><h1 style={{ color: "#923cb5" }}>Airplane Page</h1></td>
                             </tr>
                        </tbody>
                    </Table>
                </Container>
                <Container>
                    <Button style={{ backgroundColor: "#923cb5" }} onClick={() => this.toggle('showModal')}>Add new Airplane</Button>
					<Button style={{ backgroundColor: "#923cb5" }} onClick={() => window.location="/flight" }>Flights</Button>
					<Button style={{ backgroundColor: "#923cb5" }} onClick={() => window.location="/destination" }>Destinations</Button>
					<Button style={{ backgroundColor: "#923cb5" }} onClick={() => window.location="/aircompany" }>Aircompanies</Button>
					<Button style={{ backgroundColor: "#923cb5" }} onClick={() => window.location="/airplane" }>Airplanes</Button>
                    <Table >
                        <thead>
                            <tr><th>ID</th><th>Brand</th><th>Seats</th></tr>
                        </thead>
                        <tbody>
                            
                            {
                                airplanes.map((airplane) => {
                                    return <tr key={airplane.id}><td>{airplane.id}</td><td>{airplane.brand}</td><td>{airplane.seats}</td>
										<td>
												<Button style={{ backgroundColor: "#923cb5" }} value={airplane.brand} onClick={this.handleDelete}>DELETE</Button>
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