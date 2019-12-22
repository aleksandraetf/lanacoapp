import React, { Component } from 'react';
import { Button, Modal, ModalBody, InputGroup, InputGroupAddon, Container, Table, Input } from 'reactstrap';
import Moment from 'moment';
import DateTimePicker from 'react-datetime-picker';
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
		this.onChange=this.onChange.bind(this);
		

        this.state = { flights : [],aircompanies: [] ,airplanes: [] ,destinations: [],administratorAircompany: null, showModal: false, message: "", 
					date: "" , reserved: 0 ,price: 0,
					selectedAircompanyId: 0, selectedAirplaneId: 0, selectedDestinationId:0};
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
	
	async loadAircompany(){
        await fetch('/api/administrator/aircompany/',
            {
                method: 'GET',
                headers:
                {

                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    credentials: 'include'
                },
                mode:'cors',
                credentials:'include',
            }
        )
		.then(response => 
		{ 
			if (response.status != 200) 
			{
				this.setState({ message: "Nepoznata greska!"});
			} 
			return response.json();
		}).then(data=>
				this.setState({administratorAircompany:data}));	
	}

    loadData() {
        fetch('/api/flight/admin/',
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
		.then(response => 
		{ 
			if (response.status === 202) 
			{
				toast.success("Prikazane najnovije informacije!", { position: toast.POSITION_TOP_RIGHT }); 
			} else 
			{ 
				toast.error("Server nedostupan. Nisu prikazane najnovije informacije.", { position: toast.POSITION_TOP_RIGHT });
			}
			return response.json();
		})
        .then(data => {
			this.setState({flights: data});
		});
    }

    componentWillMount() {
		this.loadAircompany();
		this.loadDataAircompanies();
		this.loadDataDestinations();
		this.loadDataAirplanes();
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
        ).then(response => { if (response.status === 202) { this.loadData(); this.cleanData(); toast.success("Let uspjesno obrisan!", { position: toast.POSITION_TOP_RIGHT }); } else { toast.success("Desila se greska!", { position: toast.POSITION_TOP_RIGHT }); } });
	}

    handleInputChange(event) {
        this.setState({ [event.target.name]: event.target.value});
    }


    handleSubmit(event) {
        let dataToSend = {
			seatsReserved: this.state.reserved,
            price: this.state.price,
            flightDate: this.state.date,
            airCompany: this.state.administratorAircompany.id,
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
        ).then(response => {if (response.status === 202) { this.loadData(); this.cleanData(); this.toggle('showModal'); toast.success("Let je sacuvan.", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Let nije sacuvan!" }) } });
    }
	
	
	onChange = date => this.setState({ date })

    render() {
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
                                <InputGroup >
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Datum leta:
                                    </InputGroupAddon>
                                    <DateTimePicker
									  onChange={this.onChange}
									  value={this.state.date}
									/>
                                </InputGroup>

                                <InputGroup >
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Broj rezervisanih sjedista:
                                    </InputGroupAddon>
                                    <Input
                                        type="number" name="reserved" id="reserved" value={this.state.reserved} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>
								
								<InputGroup >
                                    <InputGroupAddon sm={3} addonType="prepend">
                                        Cijena:
                                    </InputGroupAddon>
                                    <Input
									type="number" name="price" id="price" value={this.state.price} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>
                                <br></br>
								<select ref="airplaneRef" name="custom-search-select"
									className="custom-search-select form-control">
									<option value="" selected disabled hidden> Izaberite avion </option>
									{	
										airplanes.map(
											(a)=> <option key={"a.id"} value={a.brand}>
											{a.brand}</option>)
									}
								</select>
                                <br></br>
								<select ref="destinationRef" name="custom-search-select"
									className="custom-search-select form-control">
									<option value="" selected disabled hidden> Izaberite destinaciju </option>
									{	
										destinations.map((a)=> <option key={"a.id"} value={a.name}>
											{a.name}</option>)
									}
								</select>
								
                              
                                <p style={{ color: '#923cb5' }}>{this.state.message}</p>
                                <br></br>
                                <Button style={{ backgroundColor: "#923cb5" }} onClick={this.handleSubmit}>Dodaj novi let</Button>
                            </div>
                        </ModalBody>
                    </Modal>
                </Container>
                <Container>
                    <Table>
                        <tbody>
                            <tr>
                                <td><h1 style={{ color: "#923cb5" }}>Letovi</h1></td>
                             </tr>
                        </tbody>
                    </Table>
                </Container>
                <Container>
                    <Button  onClick={() => this.toggle('showModal')}>Dodaj novi let</Button>
					<Button onClick={() => window.location="/administrator" }>Vrati se na pocetnu stranu</Button>
					<Button  onClick={this.logOut}>Log Out</Button>
                    <Table striped bordered hover >
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
										<Button value={flight.id} style={{ backgroundColor: "#923cb5" }} onClick={this.handleDelete}>Obrisi</Button>
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