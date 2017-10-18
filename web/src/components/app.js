import React from 'react';
import Home from './Home/Home.jsx';
import Pair from './Home/Pair/Pair.jsx';
import Map from './Home/Map/Map.jsx'
import Select from 'react-select';


//how do i get each key for each location?
//
//would i be able to get the keys from the server via the fetch?
//
//how do i create the itinerary?
//
//how do i create the checkboxes with the fetch?
//
//what exactly am I expanding on in this code?
//
//where should i be using the home and pair classes?
//
export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            request: "",
            svg:[],
        }
    };

    render() {

        return (

            <div className="app-container">

                <Home
                    svgmap={this.state.svg}
                    stringBuilder={this.stringBuilder.bind(this)}
                    request={this.state.request}

                />
            </div>
        )
    }

    async stringBuilder(input){
        let loc = input[0];
        let opt = input[1];
        let tag = input[2];
        let res = loc+opt+tag;
        this.setState({
            request: res
        })
    }



}
