import './App.css';
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Destination from './Destination';
import Login from './Login';
import Airplane from './Airplane';
import Aircompany from './Aircompany';
import Flight from './Flight';

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
            path="/aircompany"
            exact={true}
            render={props => {
              return (
                <Aircompany {...props} />
              );
            }}
          />
          <Route
            path="/destination"
            exact={true}
            render={props => {
              return (
                <Destination {...props} />
              );
            }}
          />
		  <Route
            path="/airplane"
            exact={true}
            render={props => {
              return (
                <Airplane {...props} />
              );
            }}
          />
		  <Route
            path="/flight"
            exact={true}
            render={props => {
              return (
                <Flight {...props} />
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
