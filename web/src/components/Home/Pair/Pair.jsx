import React, {Component} from 'react';
//I added a new variable runningDist in order to output it
class Pair extends React.Component {
    // Declare and initialize any state variables here

    constructor(props) {
        super(props);

    };

    render() {
        // Place the return statement from the stateless Pair here:
        let k = this.props.keys;
        console.log("current keys: "+k);
        let startKeys = Object.keys(this.props.startInfo.extraInfo);
        let startObjects = Object.values(this.props.startInfo.extraInfo);
        let start=[];
        // console.log("keys: "+k);
        // console.log("startkeys: "+startKeys);
        for(let i in k) {
            for (let j in startKeys){
                if (k[i] === startKeys[j] && startKeys[j] && startObjects[j]) {
                    start.push(<li>{startKeys[j]} : {startObjects[j]}</li>);
                }
            }
        }
        let endKeys=Object.keys(this.props.endInfo.extraInfo);
        let endObjects=Object.values(this.props.endInfo.extraInfo);
        let end =[];
        for(let i in k){
            for(let j in endKeys) {
                if (k[i] === endKeys[j] && endKeys[j] && endObjects[j]) {
                    end.push(<li>{endKeys[j]} : {endObjects[j]}</li>);
                }
            }
        }

        let rows = [];
            rows.push(<td><h4>{this.props.startInfo.extraInfo.name}</h4>{start} </td>);
            rows.push(<td><h4>{this.props.endInfo.extraInfo.name}</h4> {end}</td>);
            rows.push(<td><h4>{this.props.endInfo.distance}</h4></td>);
            rows.push(<td><h4>{this.props.cumDist}</h4></td>);

        return <tbody className="Pair">
        <tr>
        {rows}
        </tr>
        </tbody>;
    }

}

export default Pair;
