import {connect} from 'react-redux';
import App from './App';

function mapStateToProps(state) {
    return {
        fetchError: state.app.appState.fetchError,
    };
}

export default connect(mapStateToProps, null)(App);
