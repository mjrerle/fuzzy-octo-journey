import React from 'react';
import Home from './Home/Home.jsx';

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            pairs:[],

        }
    };

    render() {
        return (
            <div className="app-container">
                <Home
                    pairs={this.state.pairs}
                    svg={this.state.svg}
                    showSVG={this.showSVG.bind(this)}
                    dataShowItinerary={this.dataShowItinerary.bind(this)}
                />
            </div>
        )
    }

    async dataShowItinerary(data){
        //this just makes the pairs, nothing new
        console.log("data: "+data);
        let input=data;
        let pairs = [];
        let runDist = 0;
        for (let i = 0; i < input.length-1; i++) {
            runDist += parseInt(input[i+1].distance);
            let p = {
                startInfo: (input[i]),
                endInfo:(input[i+1]),
                cumDist: runDist,
            }
            pairs.push(p);
        }
        this.setState({
            pairs:pairs,
        });

    }

    async showSVG(data){
        this.setState({
            svg:data,
        })
    }
}
