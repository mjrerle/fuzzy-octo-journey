import React, {Component} from 'react';
//I added a new variable runningDist in order to output it
let Pair = ({start, end, dist,runningDist}) => <tbody
    className="pair">
    <tr>
        <td>
            <h4>{start}</h4>
        </td>
        <td>
            <h4>{end}</h4>
        </td>
        <td>
            <h4>{dist}</h4>
        </td>

        <td>
            <h4>{runningDist}</h4>
        </td>
    </tr>
</tbody>;

export default Pair;