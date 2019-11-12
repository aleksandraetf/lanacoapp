import './App.css';
import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import DestinationPage from './Destination';


class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
        
          <Route
            path="/destination"
            exact={true}
            render={props => {
              return (
                <DestinationPage {...props} />
              );
            }}
          />
         
         
        </Switch>
      </Router>
    );
  }
}

export default App;

