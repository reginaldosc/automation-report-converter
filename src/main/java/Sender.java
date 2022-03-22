public class Sender {

    private void gitCommit() {

    }

    private void gitPull() {

    }

    private void gitPush() {

    }

    public void sendFileByEmail() {

    }

    public void sendFileByGitPush() {
        // Let's pull first
        this.gitPull();

        // Git commit
        this.gitCommit();

        // Git push
        this.gitPush();
    }
}
