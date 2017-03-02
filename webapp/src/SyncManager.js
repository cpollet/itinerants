class SyncManager {
    constructor(context) {
        this.context = context;
        this.syncTimer = undefined;
    }

    listenerFactory() {
        return function () {
            if (this.context.stale() && this.syncTimer == undefined && !this.context.syncPending()) {
                this.syncTimer = setInterval(this.context.decreaseSyncTimeout, this.context.syncTimerInterval);
            }

            if (!this.context.stale() && this.syncTimer != undefined) {
                clearInterval(this.syncTimer);
                this.syncTimer = undefined;
            }

            if (this.context.stale() && this.context.syncTimedOut() && !this.context.syncPending()) {
                this.context.sync();
            }
        }.bind(this);
    }
}

export default SyncManager;
