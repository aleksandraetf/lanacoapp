import './App.css';
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import SupervisorDestination from './supervisor/Destination';
import Login from './Login';
import SupervisorAirplane from './supervisor/Airplane';
import SupervisorAircompany from './supervisor/Aircompany';
import SupervisorFlight from './supervisor/Flight';
import Supervisor from './supervisor/Supervisor';
import History from './user/History'


import User from './user/User';

import AdministratorDestination from './administrator/Destination';
import AdministratorAirplane from './administrator/Airplane';
import AdministratorFlight from './administrator/Flight';
import Administrator from './administrator/Administrator'




class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route
            path="/"
            exact={true}
            render={props => {
              return (
                <Login {...props} />
              );
            }}
          />
          <Route
            path="/supervisor/aircompany"
            exact={true}
            render={props => {
              return (
                <SupervisorAircompany {...props} />
              );
            }}
          />
          <Route
            path="/supervisor/destination"
            exact={true}
            render={props => {
              return (
                <SupervisorDestination {...props} />
              );
            }}
          />
		  <Route
            path="/supervisor/airplane"
            exact={true}
            render={props => {
              return (
                <SupervisorAirplane {...props} />
              );
            }}
          />
		  <Route
            path="/supervisor/flight"
            exact={true}
            render={props => {
              return (
                <SupervisorFlight {...props} />
              );
            }}
          />
		  <Route
            path="/supervisor"
            exact={true}
            render={props => {
              return (
                <Supervisor {...props} />
              );
            }}
          />
		  <Route
            path="/user"
            exact={true}
            render={props => {
              return (
                <User {...props} />
              );
            }}
          />
		  <Route
            path="/user/history"
            exact={true}
            render={props => {
              return (
                <History {...props} />
              );
            }}
          />
		  <Route
            path="/administrator/destination"
            exact={true}
            render={props => {
              return (
                <AdministratorDestination {...props} />
              );
            }}
          />
		  <Route
            path="/administrator/airplane"
            exact={true}
            render={props => {
              return (
                <AdministratorAirplane {...props} />
              );
            }}
          />
		  <Route
            path="/administrator"
            exact={true}
            render={props => {
              return (
                <Administrator {...props} />
              );
            }}
          />
		  <Route
            path="/administrator/flight"
            exact={true}
            render={props => {
              return (
                <AdministratorFlight {...props} />
              );
            }}
          />
          <Route
            path="/login"
            exact={true}
            render={props => {
              return (
                <Login {...props} />
              );
            }}
          />
        </Switch>
      </Router>
    );
  }
}

export default App;
