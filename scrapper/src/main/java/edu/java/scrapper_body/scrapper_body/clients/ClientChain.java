package edu.java.scrapper_body.scrapper_body.clients;

import edu.java.scrapper_body.scrapper_body.clients.github_client.GitHubCommitUpdateClient;
import edu.java.scrapper_body.scrapper_body.clients.stackoverflow_client.StackOverflowQuestionClient;
import edu.java.scrapper_body.scrapper_body.clients_body.Client;
import edu.java.scrapper_body.scrapper_body.clients_body.Response;
import java.net.URI;
import java.util.List;
import reactor.util.retry.Retry;

public final class ClientChain implements Client {
    private final Client chain;

    public ClientChain(Client chain) {
        this.chain = chain;
    }

    public static ClientChain defaultChain(Retry retry) {
        GitHubCommitUpdateClient gitHubClient = new GitHubCommitUpdateClient(retry);
        StackOverflowQuestionClient stackOverflowClient = new StackOverflowQuestionClient(gitHubClient, retry);
        return new ClientChain(stackOverflowClient);
    }

    @Override
    public List<Response> newUpdates(URI uri) {
        return chain.newUpdates(uri);
    }

}
