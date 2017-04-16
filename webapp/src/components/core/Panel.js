import React from 'react';
import styles from './Panel.css';

class Panel extends React.Component {
    render() {
        return (
            <div className={styles.component}>
                <div className={styles.title}>{this.props.title}</div>
                <div className={styles.content}>
                    {this.props.children}
                </div>
            </div>
        );
    }
}

Panel.propTypes = {
    children: React.PropTypes.node.isRequired,
    title: React.PropTypes.node
};

export default Panel;
