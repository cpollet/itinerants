import React from 'react';
import styles from './Counter.css';

/**
 * A counter button: tap the button to increase the count.
 */
class Counter extends React.Component {
    constructor() {
        super();
        this.state = {
            count: 0,
        };
    }

    render() {
        return (
            <div>
                <span className={styles.test}>Hello world!</span>
                <button
                    onClick={() => {
                        this.setState({count: this.state.count + 1});
                    }}
                >
                    Count: {this.state.count}
                </button>
            </div>
        );
    }
}
export default Counter;
