import React from 'react';
import Home from './Home/Home.jsx';

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            svg:"",
            itinerary:"",
        }
    };

    render() {
        return (
            <div className="app-container">
                <Home
                    svg={this.state.svg}
                    itinerary={this.state.itinerary}
                />
            </div>
        )
    }
}
