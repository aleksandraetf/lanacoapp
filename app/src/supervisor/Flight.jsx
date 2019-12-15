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
		this.handleDelete=this.handleDelete.bind(this);
		

        this.state = { flights : [],aircompanies: [] ,airplanes: [] ,destinations: [], showModal: false, message: "", 
					date: "" , reserved: 0 ,price: 0,
					selectedAircompanyId: 0, selectedAirplaneId: 0, selectedDestinationId:0};
		
		this.loadDataAircompanies();
		this.loadDataDestinations();
		this.loadDataAirplanes();


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

	handleDelete(event){
		console.log(event.target.value);
		let dataToSend = {
            id: event.target.value
        }
        fetch('/api/flight/',
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
        let flights = [...this.state.flights];
		let aircompanies=[...this.state.aircompanies];
		let airplanes=[...this.state.airplanes];
		let destinations=[...this.state.destinations];
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
                                <br></br>
                                <InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                       Broj sjedista za rezervaciju:
                                    </InputGroupAddon>
                                    <Input
                                        type="number" name="reserved" id="reserved" value={this.state.reserved} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>
								<br></br>
								<InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Cijena:
                                    </InputGroupAddon>
                                    <Input
									type="number" name="price" id="price" value={this.state.price} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup><br></br>
								<select ref="aircompanyRef" name="custom-search-select"
									className="custom-search-select form-control">
									<option value="" selected disabled hidden> Izaberite Aviokompaniju </option>
									{	
										aircompanies.map((a)=> <option key={"a.id"} value={a.name}>
											{a.name}</option>)
									}
								</select><br></br>
								<select ref="airplaneRef" name="custom-search-select"
									className="custom-search-select form-control">
									<option value="" selected disabled hidden> Izaberite Avion </option>
									{	
										airplanes.map(
											(a)=> <option key={"a.id"} value={a.brand}>
											{a.brand}</option>)
									}
								</select><br></br>
								<select ref="destinationRef" name="custom-search-select"
									className="custom-search-select form-control">
									<option value="" selected disabled hidden> Izaberite Destinaciju </option>
									{	
										destinations.map((a)=> <option key={"a.id"} value={a.name}>
											{a.name}</option>)
									}
								</select><br></br>
                              
                                <p style={{ color: '#923cb5' }}>{this.state.message}</p>
                                <br></br>
                                <Button style={{ backgroundColor: "#923cb5" }} onClick={this.handleSubmit}>Dodaj let</Button>
                            </div>
                        </ModalBody>
                    </Modal>
                </Container>
                <Container>
                    <Table striped bordered hover>
                        <tbody>
                            <tr>
                                <td><h1 style={{ color: "#923cb5" }}>Flight Page</h1></td>
                             </tr>
                        </tbody>
                    </Table>
                </Container>
                <Container>
                <Button onClick={this.logOut}>Log Out</Button>
                 <br></br><br></br>
                    <Button  onClick={() => this.toggle('showModal')}>Dodaj novi let</Button>
                    <br></br><br></br>
					<Button  onClick={() => window.location="/supervisor/flight" }>Letovi</Button>
					<Button onClick={() => window.location="/supervisor/destination" }>Destinacije</Button>
					<Button  onClick={() => window.location="/supervisor/aircompany" }>Avio kompanije</Button>
					<Button  onClick={() => window.location="/supervisor/airplane" }>Avioni</Button>
                    <br></br>
				   <Table striped bordered hover>
                        <thead>
                            <tr><th>Price</th><th>Reserved</th><th>Destination</th><th>Airplane</th><th>Aircompany</th><th>Date</th></tr>
                        </thead>
                        <tbody>
                            {
                                flights.map((flight) => {
                                    return <tr key={flight.id}><td>{flight.price}</td><td>{flight.seatsReserver}</td>
									<td>{flight.destination.name}</td><td>{flight.airplane.brand}</td><td>{flight.airCompany.name}</td>
									<td>{flight.flightDate}</td>
									<td>
										<Button value={flight.id} onClick={this.handleDelete}>DELETE</Button>
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