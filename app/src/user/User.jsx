import React, { Component } from 'react';
import { Button, Modal, ModalBody, InputGroup, InputGroupAddon, Container, Table, Input } from 'reactstrap';
import Moment from 'moment';

import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
import { checkIfLogged } from '../common.js';
import App from '../App'

class AirplanePage extends Component {

    constructor(props) {
        super(props);
        checkIfLogged().then(resp => {
            if (!resp) {
                this.props.history.push('/')
            }
        });
		this.logOut = this.logOut.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
		this.handleBuy=this.handleBuy.bind(this);
		this.handleHistory=this.handleHistory.bind(this);
        this.state = {flights : [], showModal: false, message: "",numberOfTickets: 0};
    }
	
	handleHistory(){
		window.location='/user/history';
	}

    loadData() {
        fetch('/api/flight/')
            .then(response => response.json())
            .then(data => this.setState({ flights: data }));
    }

    componentWillMount() {
        this.loadData();
    }

    cleanData() {
        this.setState({ message: "", date: "" , reserved: 0 ,price: 0 , selectedAircompany : ""});
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

	handleBuy(event){
		let dataToSend = {
            flight:{
				id: event.target.value
			},
			numberOfTickets: this.state.numberOfTickets
        }
        fetch('/api/ticket/',
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
        ).then(response => { 
			if (response.status === 202) {
				this.loadData();
				this.cleanData(); 
				toast.success("Uspjesno ste rezervisali karte!", { position: toast.POSITION_TOP_RIGHT }); 
			} else if(response.status==500){
				toast.error("Server nedostupan.", { position: toast.POSITION_TOP_RIGHT }); 
			}else{
				toast.error("Nije moguce rezervisati trazeni broj karata!", { position: toast.POSITION_TOP_RIGHT }); 
			}
			});
	}

    handleInputChange(event) {
        this.setState({ [event.target.name]: event.target.value});
    }


    render() {
        let flights = [...this.state.flights];
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
                                        Datum leta:
                                    </InputGroupAddon>
                                    <Input
                                        type="date" name="date" id="date" value={this.state.date} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>

                                <InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Broj rezervisanih sjedista:
                                    </InputGroupAddon>
                                    <Input
                                        type="number" name="reserved" id="reserved" value={this.state.reserved} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>
								
								<InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Cijena:
                                    </InputGroupAddon>
                                    <Input
									type="number" name="price" id="price" value={this.state.price} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>
								
                              
                                <p style={{ color: '#923cb5' }}>{this.state.message}</p>
                                <br></br>
                                <Button  onClick={this.handleSubmit}>Dodaj let</Button>
                            </div>
                        </ModalBody>
                    </Modal>
                </Container>
                <Container>
                    <Table>
                        <tbody>
                            <tr>
                                <td><h1 style={{ color: "#923cb5" }}>Kupi karte</h1></td>
                             </tr>
                        </tbody>
                    </Table>
                </Container>
                <Container>
					<Button onClick={this.logOut}>Log Out</Button><br></br><br></br>
					<Button onClick={this.handleHistory}>Istorija</Button><br></br><br></br>
					<InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Broj karata :
                                    </InputGroupAddon><br></br><br></br>
                                    <Input
                                        type="number" name="numberOfTickets" id="numberOfTickets" value={this.state.numberOfTickets} onChange={this.handleInputChange}
                                    ></Input> <br></br><br></br>
                    </InputGroup>
                    <Table striped hover bordered >
                        <thead>
                            <tr><th>Cijena</th><th>Broj rezervisanih sjedista</th><th>Ukupan broj sjedista</th><th>Destinacija</th><th>Avion</th><th>Avio kompanija</th><th>Datum</th></tr>
                        </thead>
                        <tbody>
                            {
                                flights.map((flight) => {
									Moment.locale('en');
                                    return <tr key={flight.id}><td>{flight.price}</td><td>{flight.seatsReserved}</td><td>{flight.airplane.seats}</td>
									<td>{flight.destination.name}</td><td>{flight.airplane.brand}</td><td>{flight.airCompany.name}</td>
									<td>{Moment(flight.flightDate).format('YYYY-MM-DD : hh:MM:ss')}</td>
									<td>
										<Button value={flight.id} style={{ backgroundColor: "#923cb5" }} onClick={this.handleBuy}>Kupi</Button>
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