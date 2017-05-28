import React from 'react';
import styles from './Circle.less';

class Circle extends React.Component {
    render() {
        return (
            <i className={styles.component} style={{
                color: this.props.color
            }}/>
        );
    }
}

Circle.propTypes = {
    color: React.PropTypes.string.isRequired,
};

Circle.defaultProps ={
    color: 'black',
};

export default Circle;
