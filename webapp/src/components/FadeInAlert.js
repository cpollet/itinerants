import React from 'react';
import FadeIn from './animation/FadeIn';
import Alert from './core/Alert';

class FadeInAlert extends React.Component {
    render() {
        return (
            <FadeIn><Alert {...this.props}/></FadeIn>
        );
    }
}

export default FadeInAlert;
