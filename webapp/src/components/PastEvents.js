import React from 'react';
import Events from './Events';

class PastEvents extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <Events when="past" events={[]}/>
        );
    }
}
export default PastEvents;
