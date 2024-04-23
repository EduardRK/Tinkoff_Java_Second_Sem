package edu.java.service.send_update;

import edu.java.requests.LinkUpdateRequest;
import java.util.List;

public interface SendUpdateService {
    void send(LinkUpdateRequest linkUpdateRequest);

    void send(List<LinkUpdateRequest> linkUpdateRequestList);
}
