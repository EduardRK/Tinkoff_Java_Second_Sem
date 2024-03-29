package edu.java.service.scrapper_body.clients;

import edu.java.service.scrapper_body.clients.github_client.GitHubCommitUpdateClient;
import edu.java.service.scrapper_body.clients.stackoverflow_client.StackOverflowQuestionClient;
import edu.java.service.scrapper_body.clients_body.Client;
import edu.java.service.scrapper_body.clients_body.Response;
import java.net.URI;
import java.util.List;

public final class ClientChain implements Client {
    private final Client chain;

    public ClientChain(Client chain) {
        this.chain = chain;
    }

    public static ClientChain defaultChain() {
        GitHubCommitUpdateClient gitHubClient = new GitHubCommitUpdateClient();
        StackOverflowQuestionClient stackOverflowClient = new StackOverflowQuestionClient(gitHubClient);
        return new ClientChain(stackOverflowClient);
    }

    @Override
    public List<Response> newUpdates(URI uri) {
        return chain.newUpdates(uri);
    }

}
