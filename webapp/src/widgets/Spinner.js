import React from 'react';
import styles from './Spinner.less';

class Spinner extends React.Component {
    render() {
        return (
            <div className={styles.component}>
                <img src="/img/preload.gif"/>
            </div>
        );
    }
}

export default Spinner;
