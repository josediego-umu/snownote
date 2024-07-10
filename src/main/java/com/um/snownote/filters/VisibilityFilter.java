package com.um.snownote.filters;

import com.mongodb.DBRef;
import com.um.snownote.dto.ProjectDTO;
import com.um.snownote.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;

public class VisibilityFilter implements Filter<ProjectDTO> {

    private final String visibility;
    private final String field = "visibility";

    private final String readers = "readers.$id";
    private final String writers = "writers.$id";
    private final String owner = "owner.$id";
    private final String privateV = "private";
    private final String publicV = "public";
    private final User user;

    public VisibilityFilter(String visibility, User user) {
        this.visibility = visibility;
        this.user = user;
    }

    @Override
    public Criteria toCriteria() {

        if (visibility == null || visibility.isEmpty())
            return null;

        return switch (visibility) {
            case "public" -> getPublicCriteria();
            case "private" -> getPrivateCriteria();
            case "all" -> getAllCriteria();
            default -> null;
        };

    }

    private Criteria getPrivateCriteria() {

        ObjectId userId;

        if (user == null) {
            userId = new ObjectId("000000000000000000000000");

        } else {
            userId = new ObjectId(user.getId());
        }

        return Criteria.where(field).is(privateV)
                .orOperator(
                        Criteria.where(owner).is(userId),
                        Criteria.where(readers).is(userId),
                        Criteria.where(writers).is(userId));
    }

    private Criteria getPublicCriteria() {
        return Criteria.where(field).is(publicV);
    }

    private Criteria getAllCriteria() {
        return new Criteria().orOperator(getPublicCriteria(), getPrivateCriteria());
    }
}
