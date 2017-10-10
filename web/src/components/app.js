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
        /*allPairs: array of all pairs(raw data), sysFile: json file, svgfile: map, toggleSVG: when to show the svg*/
    };

    render() {
        let sm = <Map source={this.state.svgFile} style={this.state.toggleSVG ? {} : {display: "none"}}/>;
        console.log(sm);
        console.log(this.state.toggleSVG);
        console.log(document.getElementById('map'));
        let pairs = this.state.allPairs;
        let ps = pairs.map((pp) => {
            return <Pair {...pp}/>;
        });
        {/*^man im so clever... only show the svg when it has been uploaded (modifying the display attribute)*/
        }
        {/*Home accepts browseFile method in its parent(app.js) as well as the pairs(will be populated), browseSVG and finally the map to manipulate*/
        }
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
        /*For loop that goes through all pairs,*/
        let pairs = [];
        let runDist = 0;
        /*new variable to keep track of running distance*/
        for (let i = 0; i < Object.values(file).length - 1; i++) {
            let dist = file[i + 1].distance;
            runDist += parseInt(dist);
            /*gotta keep that running distance running*/
            let p = {
                startInfo: file[i],
                endInfo: file[i + 1],
                cumDist: runDist
            };
            /*create object with start, end, and dist variable*/
            pairs.push(p); //add object to pairs array*/}
            console.log("Pushing pair: ", p);
            /*log to console*/
        }


        /*Here we will update the state of app.*/
        /*Anything component (i.e. pairs) referencing it will be re-rendered*/
        this.setState({
            allPairs: pairs,
            sysFile: file
        });
    }

    async browseSVG(file) {
        /*console.log("Got svg: ", file);*/
        this.setState({
            toggleSVG: true,
            svgFile: file
        });
    }


}
