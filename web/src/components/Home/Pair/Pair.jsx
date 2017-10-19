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
        let startKeys = Object.keys(this.props.startInfo);
        let startObjects = Object.values(this.props.startInfo);
        let start=[];
        // console.log("keys: "+k);
        // console.log("startkeys: "+startKeys);
        for(let i in k) {
            for (let j in k){
                if (k[i] === startKeys[j] && startKeys[i] && startObjects[i]) {
                    start.push(<li>{startKeys[i]} : {startObjects[i]}</li>);
                }
            }
        }
        let endKeys=Object.keys(this.props.endInfo);
        let endObjects=Object.values(this.props.endInfo);
        let end =[];
        for(let i in k){
            for(let j in k) {
                if (k[i] === endKeys[j] && endKeys[i] && endObjects[i]) {
                    end.push(<li>{endKeys[i]} : {endObjects[i]}</li>);
                }
            }
        }

        let rows = [];
            rows.push(<td><h4>{this.props.startInfo.name}</h4>{start} </td>);



            rows.push(<td><h4>{this.props.endInfo.name}</h4> {end}</td>);
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
