import React from 'react';
import Home from './Home/Home.jsx';
import Pair from './Home/Pair/Pair.jsx';


export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allPairs: [],
            sysFile: []
        }
    };

    render() {
        let pairs = this.state.allPairs;
        let ps = pairs.map((pp) => {
            return <Pair {...pp}/>;
        });
        return (
            <div className="app-container">
                <Home
                    browseFile={this.browseFile.bind(this)}
                    pairs={ps}
                />
            </div>
        )
    }

    async browseFile(file) {
        console.log("Got file:", file);
        //For loop that goes through all pairs,
        let pairs = [];
        let runDist = 0; //new variable to keep track of running distance
        for (let i = 0; i < Object.values(file).length; i++) {
            // New edit for new JSON
            if(i < Object.values(file).length -1){
	            let start = file[i].name; //.start; //get start from file i
        	    let end = file[i+1].name; //.end; //get end from file i
	            let dist = file[i+1].distance;
        	    let runningDist = (runDist + parseInt(dist) ); //floating points suck in JS
            	    runDist += parseInt(dist);//gotta keep that running distance running
           	    let p = { //create object with start, end, and dist variable
                    start: start,
                    end: end,
                    dist: dist,
                    runningDist: runningDist
            	};
            	pairs.push(p); //add object to pairs array
            	console.log("Pushing pair: ", p); //log to console
        	}
		else {
		
		}
	}

        //Here we will update the state of app.
        // Anything component (i.e. pairs) referencing it will be re-rendered
        this.setState({
            allPairs: pairs,
            sysFile: file
        });
    }
}
