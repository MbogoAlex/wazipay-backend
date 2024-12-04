package com.escrow.wazipay.media.dao;

import com.escrow.wazipay.media.entity.VerificationImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VerificationImageDaoImpl implements VerificationImageDao{
    private final EntityManager entityManager;
    @Autowired
    public VerificationImageDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public VerificationImage uploadImage(VerificationImage verificationImage) {
        entityManager.persist(verificationImage);
        return verificationImage;
    }

    @Override
    public VerificationImage updateImage(VerificationImage verificationImage) {
        entityManager.merge(verificationImage);
        return verificationImage;
    }

    @Override
    public VerificationImage getImageByImageId(Integer imageId) {
        TypedQuery<VerificationImage> query = entityManager.createQuery("from VerificationImage where id = :id", VerificationImage.class);
        query.setParameter("id", imageId);
        return query.getSingleResult();
    }

    @Override
    public List<VerificationImage> getUserVerificationImages(Integer userId) {
        TypedQuery<VerificationImage> query = entityManager.createQuery("from VerificationImage where user.id = :userId", VerificationImage.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public String deleteImage(Integer imageId) {
        Query query = entityManager.createQuery("delete from VerificationImage where id = :id");
        query.setParameter("id", imageId);
        int result = query.executeUpdate();
        return "Deleted "+result+" rows";
    }
}
