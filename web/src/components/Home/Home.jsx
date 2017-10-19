import React, {Component} from 'react';
// import Dropzone from 'react-dropzone';
import Select from 'react-select';
import Pair from './Pair/Pair.jsx';
import InlineSVG from "svg-inline-react";

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            serverReturned: null,
            tags: [],
            destinations: [],
            op_level: "Nearest Neighbor",
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
        let svg;
        let svgmap;
        let dests;
        let destinations;
        let cols;
        let columns;
        let opt_options;
        let pairs;
        let p;
        let renderedSVG;
        if (this.state.serverReturned && this.props.svg) {
            let array = this.props.pairs;
            {/*assigned it to a variable to make it easier to read, still insane*/
            }
            // console.log("pairs: "+array[3].endInfo.distance);
            for (let i = 0; i < array.length - 1; i++) {
                total += (array[i + 1].distance);
            }
            pairs = this.props.pairs;
            p = pairs.map((pp) => {
                return <Pair keys={this.state.serverReturned.columns} cumDist={pp.cumDist} startInfo={pp.startInfo}
                             endInfo={pp.endInfo}/>;
            });
            console.log(this.props.svg.contents);
            renderedSVG = <InlineSVG src={this.props.svg}>SVG</InlineSVG>;
                        // dests = this.state.destinations;
            // destinations = dests.map((d) => {
            //     return <td><h4>{...d}</h4></td>;
            // });
            cols = this.state.tags;
            // columns = cols.map((cc) => {
            //     return <td><h4>{...cc}</h4></td>;
            // });
        }
        return (<div className="home-container">
                <div className="inner">
                    <h2>Team 29 - SPB</h2>
                    <h3>Itinerary Builder 3.0</h3>
                    {/*<Dropzone className="dropzone-style" onDrop={this.drop.bind(this)}>
                    <button>Open JSON File</button>
                </Dropzone>
                <Dropzone className="dropzone-style" onDrop={this.renderSVG.bind(this)}>
                    <button>Open SVG File</button>
                </Dropzone>
                */}
                    <h4>Search</h4>
                    <input className="search-button" type="search" placeholder="Enter a search like denver"
                           onKeyUp={this.handleSearchEvent.bind(this)} autoFocus/><br/>
                    <label><h4>Choose Optimization Level</h4>
                        <label>Nearest Neighbor<input name="opt-level" type="radio" value={"Nearest Neighbor"}
                                                      checked={this.state.op_level === "Nearest Neighbor"}
                                                      onChange={this.handleOptimization.bind(this)}/></label><br/>
                        <label>2-Opt<input name="opt-level" type="radio" value={"2-Opt"}
                                           checked={this.state.op_level === "2-Opt"}
                                           onChange={this.handleOptimization.bind(this)}/></label>
                    </label>

                    <h4>Show extra information</h4>
                    <Select
                        options={cols}
                        multi
                        value={this.state.tags}
                        onChange={this.handleTagSearch.bind(this)}/><br/>
                    <h4>My Trip</h4>
                    <button name="show-itinerary" onClick={this.handleShowItinerary.bind(this)}>Show Trip</button>
                    {renderedSVG}
                    <table className="pair-table">
                        <tr>
                            <td><h4>Start</h4></td>
                            <td><h4>End</h4></td>
                            <td><h4>Distance (mi)</h4></td>
                            <td><h4>Running Total (mi)</h4></td>
                        </tr>
                        {p}
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
            op_level: this.state.op_level,
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
            console.log("serverret: " + this.state.serverReturned.locs);

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
        event.preventDefault();
        let input = this.state.serverReturned;
        this.props.dataShowItinerary(input.locs);
        /*this method acts similar to drop: accept file into an array: file[0] will be evaluated as a data file instead */
        this.props.showSVG(this.state.serverReturned.svg);
                /*calling the parent method(in app.js)*/
    }

    async handleTagSearch(event){
        let input = event.target.value;
        let a = this.state.tags;
        a.push(input);
        this.setState({
            tags: a,
        });
    }

    async handleOptimization(event) {
        let input = event.target.value;
        this.setState({
            op_level: input,
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