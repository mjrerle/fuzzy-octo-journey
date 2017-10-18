import React, {Component} from 'react';
// import Dropzone from 'react-dropzone';
import Select from 'react-select';
import 'react-select/dist/react-select.css';


class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            serverReturned: null,
            tags: [],
            destinations: [],
            pairs: [],
            opt_level: [],
            svg: "",
        }
    }
    /*DEPRECATED circa Sprint 2*/

// renderSVG(myfiles) {
//     /*this method acts similar to drop: accept file into an array: file[0] will be evaluated as a data file instead */
//     console.log("Accepting drop(svg)");
//     myfiles.forEach(file => {
//         console.log("Filename: ", file.name, "File: ", file);
//         let fr = new FileReader();
//         fr.onload = (function () {
//             /*when calling the read, create anonymous function*/
//             return function (e) {
//                 /*anonymous function returns another function which handles the event*/
//                 let rawSVG = e.target.result;
//                 /*obviously don't parse as a json, just call the parent method*/
//                 /*console.log("m: ",m);*/
//                 this.props.browseSVG(rawSVG);
//                 /*calling the parent method(in app.js)*/
//             };
//         })(file).bind(this);
//         /*must bind the file to this*/
//         fr.readAsDataURL(file);
//         /*most important part*/
//     });
// }
// drop(acceptedFiles) {
//     console.log("Accepting drop");
//     acceptedFiles.forEach(file => {
//         console.log("Filename:", file.name, "File:", file);
//         console.log(JSON.stringify(file));
//         let reader = new FileReader();
//         reader.onload = (function () {
//             return function (e) {
//                 let JsonObj = JSON.parse(e.target.result);
//                 console.log(JsonObj);
//                 this.props.browseFile(JsonObj);
//             };
//         })(file).bind(this);
//
//         reader.readAsText(file);
//     });
// }
    render() {
        let total = 0;
        {/*update the total here*/
        }

        let objectArray = this.state.pairs;
        {/*assigned it to a variable to make it easier to read, still insane*/
        }

        for (let i = 0; i < objectArray.length; i++) {
            {/*parsing through objects and adding distances together*/
            }
            total *= 100000;

            total += (objectArray[i].props.endInfo.distance * 100000);

            total /= 100000;
            {/*because why would floating-point addition work at all without this*/
            }
        }

        let svg;
        let svgmap;
        let dests;
        let destinations;
        let cols;
        let columns;
        let opt_options;
        let pairs;
        let p;
        if (this.state.serverReturned) {
            pairs = this.state.pairs;
            p = pairs.map((pp) => {
                return <Pair keys={this.state.serverReturned.columns}{...pp}/>;
            });
            svg = this.state.svg;
            svgmap = <Map source={svg}/>;
            dests = this.state.destinations;
            destinations = dests.map((d) => {
                return <td><h4>{...d}</h4></td>;
            });
            cols = this.state.tags;
            columns = cols.map((cc) => {
                return <td><h4>{...cc}</h4></td>;
            });
            opt_options = [{value: 'NN', label: 'Nearest Neighbor'}, {value: '2-opt', label: '2-opt'}];
        }
        return (<div className="home-container">
                <div className="inner">
                    <h2>Team 29 - SPB</h2>
                    <h3>Itinerary</h3>
                    {/*<Dropzone className="dropzone-style" onDrop={this.drop.bind(this)}>
                    <button>Open JSON File</button>
                </Dropzone>
                <Dropzone className="dropzone-style" onDrop={this.renderSVG.bind(this)}>
                    <button>Open SVG File</button>
                </Dropzone>
                */}

                    <input className="search-button" type="text" placeholder="Enter a search like denver"
                           onKeyUp={this.handleSearchEvent.bind(this)} autoFocus/>
                    <Select name="opt-level" value="Choose Optimization Level" options={opt_options}
                            onChange={this.handleOptimization.bind(this)}/>

                    <table className="destinations-table">
                        <tr>
                            {columns}
                        </tr>
                        <tbody>
                        <tr>
                            {destinations}
                        </tr>
                        </tbody>
                    </table>
                    <Select name="attributes" value="Choose Attributes" options={cols}
                            onChange={this.handleTagSearch.bind(this)}/>
                    <button name="show-itinerary" onChange={this.handleShowItinerary.bind(this)}>Show Trip</button>
                    {svgmap}
                    <table className="pair-table">
                        <tr>
                            <td><h4>Start</h4></td>
                            <td><h4>End</h4></td>
                            <td><h4>Distance (mi)</h4></td>
                            <td><h4>Running Total (mi)</h4></td>
                        </tr>
                        {pairs}
                        <tbody>
                        <tr>
                            <td colSpan="3"><h3>Total miles:</h3></td>
                            <td>{total}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        )
    };

    async fetch(input) {
        // Create object to send to server
        /*  IMPORTANT: This object must match the structure of whatever
            object the server is reading into (in this case DataClass) */
        let newMap = {
            query: input,
            opt_level: this.state.opt_level,
        };
        try {
            // Attempt to send `newMap` via a POST request
            // Notice how the end of the url below matches what the server is listening on (found in java code)
            // By default, Spark uses port 4567
            let jsonReturned = await
                fetch(`http://localhost:4567/testing`,
                    {
                        method: "POST",
                        body: JSON.stringify(newMap)
                    });
            // Wait for server to return and convert it to json.
            let ret = await
                jsonReturned.json();
            this.setState({
                serverReturned: JSON.parse(ret),
            });
            /*serverReturned has svg, locations, columns*/

            // Log the received JSON to the browser console
            console.log("Got back ", JSON.parse(ret));
            // set the serverReturned state variable to the received json.
            // this way, attributes of the json can be accessed via this.state.serverReturned.[field]

            // Update the state so we can see it on the web
        } catch (e) {
            console.error("Error talking to server");
            console.error(e);
        }
    }

    async handleShowItinerary(event) {
        let input = this.state.serverReturned.locations;
        let pairs = [];
        let runDist = 0;
        for (let i = 0; i < input.length; i++) {
            runDist += parseInt(input[i + 1].distance);
            let p = {
                startInfo: input[i],
                endInfo: input[i + 1],
                cumDist: runDist,
            }
            pairs.push(p);
        }
        this.setState({
            svg: this.state.serverReturned.svg,
            pairs: pairs,
        })
    }

    async handleTagSearch(event) {
        let input = event.target.value;
        let a = [];
        a.push(input);
        this.setState({
            tags: a,
        });
    }

    async handleOptimization(event) {
        let input = event.target.value;
        this.setState({
            opt_level: input,
        });
    }

    // This function waits until enter is pressed on the event (input)
    // A better implementation would be to have a Javascript form with an onSubmit event that calls fetch
    async handleSearchEvent(event) {
        if (event.which === 13) { // Waiting for enter to be pressed. Enter is key 13: https://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
            this.fetch(event.target.value); // Call fetch and pass whatever text is in the input box
        }
    }
}
export default Home