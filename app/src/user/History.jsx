import React, { Component } from 'react';
import { Button, Modal, ModalBody, InputGroup, InputGroupAddon, Container, Table, Input } from 'reactstrap';
import Moment from 'moment';

import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
import { checkIfLogged } from '../common.js';
import App from '../App'
import User from './User'

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
		this.handleHistory=this.handleHistory.bind(this);
		this.fetchTickets=this.fetchTickets.bind(this);
		
		console.log(sessionStorage.getItem("email"));
		
        this.state = {tickets : [], showModal: false, message: "",numberOfTickets: 0};
		this.fetchTickets();
    }
	
	fetchTickets(){
		let dataToSend='test@gmail.com';
		console.log(dataToSend);
        fetch('/api/ticket/user/',
            {
                method: 'GET',
                headers:
                {

                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    credentials: 'include'
                },
                mode:'cors',
                credentials:'include'
            }
        )
            .then(response => response.json())
            .then(data => this.setState({ tickets: data }));
		console.log('Tickeeeeeeeeeets:')
		console.log(this.state.tickets);
	}
	
	handleHistory(){
		window.location='/user/history';
	}
	
	loadDataAircompanies() {
        fetch('/api/aircompany/')
            .then(response => response.json())
            .then(data => this.setState({ aircompanies: data }));
    }
	
	loadDataDestinations() {
        fetch('/api/destination/')
            .then(response => response.json())
            .then(data => this.setState({ destinations: data }));
    }
	loadDataAirplanes() {
        fetch('/api/airplane/')
            .then(response => response.json())
            .then(data => this.setState({ airplanes: data }));
    }

    loadData() {
		
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

    handleInputChange(event) {
        this.setState({ [event.target.name]: event.target.value});
    }


    handleSubmit(event) {
		
		
        let dataToSend = {
            price: this.state.price,
            flightDate: this.state.date,
            airCompany: this.state.aircompanies.filter((a)=>{return a.name===this.refs.aircompanyRef.value})[0].id, //this.state.aircompanyId,
            airplane:this.state.airplanes.filter((a)=>{return a.brand===this.refs.airplaneRef.value})[0].id, // this.state.airplaneId,
            destination:this.state.destinations.filter((a)=>{return a.name===this.refs.destinationRef.value})[0].id// this.state.destinationId
        }

        fetch('/api/flight/',
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
        ).then(response => {if (response.status === 202) { this.loadData(); this.cleanData(); this.toggle('showModal'); toast.success("Flight Saved", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Airplane not saved! Fields can not be empty and it is not possible to add existing Airplane!" }) } });
    }

    render() {
        console.log(this.state);
        let tickets = [...this.state.tickets];
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
                                        Flight Date:
                                    </InputGroupAddon>
                                    <Input
                                        type="date" name="date" id="date" value={this.state.date} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>

                                <InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Reserved Seats:
                                    </InputGroupAddon>
                                    <Input
                                        type="number" name="reserved" id="reserved" value={this.state.reserved} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>
								
								<InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Price:
                                    </InputGroupAddon>
                                    <Input
									type="number" name="price" id="price" value={this.state.price} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>
								
                              
                                <p style={{ color: '#923cb5' }}>{this.state.message}</p>
                                <br></br>
                                <Button style={{ backgroundColor: "#923cb5" }} onClick={this.handleSubmit}>Add Flight</Button>
                            </div>
                        </ModalBody>
                    </Modal>
                </Container>
                <Container>
                    <Table>
                        <tbody>
                            <tr>
                                <td><h1 style={{ color: "#923cb5" }}>Istorija</h1></td>
                             </tr>
                        </tbody>
                    </Table>
                </Container>
                <Container>
					<Button style={{ backgroundColor: "#923cb5" }} onClick={this.logOut}>Log Out</Button>
                    <Table >
                        <thead>
                            <tr><th>ID</th><th>Price</th><th>Reserved</th><th>Destination</th><th>Airplane</th><th>Aircompany</th><th>Date</th></tr>
                        </thead>
                        <tbody>
                            {
                                tickets.map((ticket) => {
									Moment.locale('en');
                                    return <tr key={ticket.id}><td>{ticket.id}</td><td>{ticket.flight.price}</td><td>{ticket.numberOfTickets}</td>
									<td>{ticket.flight.destination.name}</td><td>{ticket.flight.airplane.brand}</td><td>{ticket.flight.airCompany.name}</td>
									<td>{Moment(ticket.flight.flightDate).format('YYYY-MM-DD : hh:MM:ss')}</td>
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