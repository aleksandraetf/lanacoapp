import React, { Component } from 'react';
import { Button, Modal, ModalBody, InputGroup, InputGroupAddon, Container, Table, Input } from 'reactstrap';
import Moment from 'moment';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
import { checkIfLogged } from '../common.js';
import DateTimePicker from 'react-datetime-picker';

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
		

        this.state = {administrators: [], flights : [],aircompanies: [] ,airplanes: [] ,destinations: [], showModal: false, message: "", 
					date: "" , reserved: 0 ,price: 0,
					selectedAircompanyId: 0, selectedAirplaneId: 0, selectedDestinationId:0,email: '',password: ''};
		
    }
	
	loadDataAircompanies() {
        fetch('/api/aircompany/')
            .then(response => response.json())
            .then(data => this.setState({ aircompanies: data }));
    }

    loadData() {
        fetch('/api/administrator/')
            .then(response => response.json())
            .then(data => this.setState({ administrators: data }));
    }

    componentWillMount() {
		this.loadDataAircompanies();
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

	onChange = date => this.setState({ date })

	handleDelete(event){
		let dataToSend =this.state.administrators.filter((a)=>a.id==event.target.value)[0].email;
        fetch('/api/administrator/',
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
                body: dataToSend,
            }
        ).then(response => { if (response.status === 202) { this.loadData(); this.cleanData(); toast.success("Administrator uspjeno obrisan!", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Greska."}) } });
	}

    handleInputChange(event) {
        this.setState({ [event.target.name]: event.target.value});
    }


    handleSubmit(event) {
		let dataToSend=null;
		try{
        dataToSend = {
			email:this.state.email,
			password: this.state.password,
			airCompany: this.refs.aircompanyRef.value
        }
		}catch(e){
			toast.error("Nisu sva polja popunjena!", { position: toast.POSITION_TOP_RIGHT });
			return;
		}

        fetch('/api/administrator/',
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
        ).then(response => {if (response.status === 202) { this.loadData(); this.cleanData(); this.toggle('showModal'); toast.success("Administrator sacuvan!", { position: toast.POSITION_TOP_RIGHT }); } else { this.setState({ message: "Administrator nije sacuvan!" }) } });
    }

    render() {
		let aircompanies=[...this.state.aircompanies];
		let administrators=[...this.state.administrators];
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
                                       Email:
                                    </InputGroupAddon>
                                    <Input
                                        type="text" name="email" id="email" value={this.state.email} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>

                                <InputGroup size="sm">
                                    <InputGroupAddon sm={3} addonType="prepend">
                                       Password:
                                    </InputGroupAddon>
                                    <Input
                                        type="password" name="password" id="password" value={this.state.password} onChange={this.handleInputChange}
                                    ></Input>
                                </InputGroup>
								
								<select ref="aircompanyRef" name="custom-search-select"
									className="custom-search-select">
									<option value="" selected disabled hidden> Izaberite avio kompaniju </option>
									{	
										aircompanies.map((a)=> <option key={"a.id"} value={a.name}>
											{a.name}</option>)
									}
								</select>
                                <p style={{ color: '#923cb5' }}>{this.state.message}</p>
                                <br></br>
                                <Button style={{ backgroundColor: "#923cb5" }} onClick={this.handleSubmit}>Dodaj administratora</Button>
                            </div>
                        </ModalBody>
                    </Modal>
                </Container>
                <Container>
                    <Table>
                        <tbody>
                            <tr>
                                <td><h1 style={{ color: "#923cb5" }}>Administratori</h1></td>
                             </tr>
                        </tbody>
                    </Table>
                </Container>
                <Container>
                    <Button onClick={this.logOut}>Odjavi se</Button>
                 <br></br><br></br>
                    <Button  onClick={() => this.toggle('showModal')}>Dodaj novog administratora</Button>
                    <br></br><br></br>
					<Button  onClick={() => window.location="/supervisor" }>Vrati se na pocetnu</Button>
                    <br></br>
				   <Table striped bordered hover>
                        <thead>
                            <tr><th>Id</th><th>email</th><th>Aviokompanija</th><th>Obrisi</th></tr>
                        </thead>
                        <tbody>
                            {
                                administrators.map((admin) => {
									Moment.locale('en');
                                    return <tr key={admin.id}><td>{admin.id}</td><td>{admin.email}</td><td>{admin.airCompany.name}</td>
									<td>
										<Button value={admin.id} style={{ backgroundColor: "#923cb5" }} onClick={this.handleDelete}>Obrisi</Button>
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