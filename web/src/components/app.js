import React from 'react';
import Home from './Home/Home.jsx';
import Pair from './Home/Pair/Pair.jsx';
import Map from './Home/Map/Map.jsx'


export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allPairs: [],
            sysFile: [],
            svgFile: [],
            toggleSVG: false
        }
    };

    render() {
        let sm = <Map source={this.state.svgFile} style={this.state.toggleSVG ? {} : {display: "none"}}/>
   		console.log(sm);
		console.log(this.state.toggleSVG);
		console.log(document.getElementById('map'));
        let pairs = this.state.allPairs;
        let ps = pairs.map((pp) => {
            return <Pair {...pp}/>;
        });
        return (
            <div className="app-container">
                <Home
                    browseFile={this.browseFile.bind(this)}
                    pairs={ps}
                    browseSVG={this.browseSVG.bind(this)}
                    svgmap={sm}
                />
            </div>
        )
    }

    async browseFile(file) {
        console.log("Got file:", file);
        //For loop that goes through all pairs,
        let pairs = [];
        let runDist = 0; //new variable to keep track of running distance
        for (let i = 0; i < Object.values(file).length -1; i++) {
            let dist = file[i+1].distance;
            runDist += parseInt(dist);//gotta keep that running distance running
            let p = { //create object with start, end, and dist variable
                startInfo: file[i],
                endInfo: file[i+1],
                cumDist: runDist
            };
            pairs.push(p); //add object to pairs array
            console.log("Pushing pair: ", p); //log to console
        }


        //Here we will update the state of app.
        // Anything component (i.e. pairs) referencing it will be re-rendered
        this.setState({
            allPairs: pairs,
            sysFile: file
        });
    }

	async browseSVG(file){
		//console.log("Got svg: ", file);
		this.setState({
			toggleSVG: true,
			svgFile: file
		});
	}
		
	
}
