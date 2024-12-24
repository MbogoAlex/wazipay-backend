package com.escrow.wazipay.dao.user;

import com.escrow.wazipay.model.user.UserAccount;
import com.escrow.wazipay.model.user.UserVerification;
import com.escrow.wazipay.model.user.VerificationFile;
import com.escrow.wazipay.model.user.VerificationStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{
    private final EntityManager entityManager;
    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        entityManager.persist(userAccount);
        return userAccount;
    }

    @Override
    public UserAccount updateUser(UserAccount userAccount) {
        entityManager.merge(userAccount);
        return userAccount;
    }

    @Override
    public UserAccount getUserById(Integer userId) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where id = :userId", UserAccount.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }

    @Override
    public UserAccount getUserByEmail(String email) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where email = :email", UserAccount.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public UserAccount getUserByPhoneNumber(String phoneNumber) {
        TypedQuery<UserAccount> query = entityManager.createQuery("from UserAccount where phoneNumber = :phoneNumber", UserAccount.class);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getSingleResult();
    }

    @Override
    public Boolean userExists(String phoneNumber, String email) {
        String queryStr = "SELECT COUNT(u) FROM UserAccount u WHERE u.phoneNumber = :phoneNumber OR u.email = :email";
        Long count = entityManager.createQuery(queryStr, Long.class)
                .setParameter("phoneNumber", phoneNumber)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public VerificationFile uploadFile(VerificationFile verificationFile) {
        entityManager.persist(verificationFile);
        return verificationFile;
    }

    @Override
    public VerificationFile updateFile(VerificationFile verificationFile) {
        entityManager.merge(verificationFile);
        return verificationFile;
    }

    @Override
    public VerificationFile getFileById(Integer fileId) {
        TypedQuery<VerificationFile> query = entityManager.createQuery("from VerificationFile where id = :id", VerificationFile.class);
        query.setParameter("id", fileId);
        return query.getSingleResult();
    }

    @Override
    public List<VerificationFile> getUserVerificationFiles(Integer userId) {
        TypedQuery<VerificationFile> query = entityManager.createQuery("from VerificationFile where user.id = :userId", VerificationFile.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public String deleteFile(Integer fileId) {
        Query query = entityManager.createQuery("delete from VerificationFile where id = :id");
        query.setParameter("id", fileId);
        int result = query.executeUpdate();
        return "Deleted "+result+" rows";
    }

    @Override
    public UserVerification createUserVerification(UserVerification userVerification) {
        entityManager.persist(userVerification);
        return userVerification;
    }

    @Override
    public UserVerification updateUserVerification(UserVerification userVerification) {
        entityManager.merge(userVerification);
        return userVerification;
    }

    @Override
    public UserVerification getUserVerificationDetails(Integer userId) {
        TypedQuery<UserVerification> query = entityManager.createQuery("from UserVerification where userAccount.id = :userId", UserVerification.class);
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }

    @Override
    public UserVerification getVerificationDetailsById(Integer id) {
        TypedQuery<UserVerification> query = entityManager.createQuery("from UserVerification where id = :id", UserVerification.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<UserVerification> getAllVerificationDetails() {
        TypedQuery<UserVerification> query = entityManager.createQuery("from UserVerification", UserVerification.class);
        return query.getResultList();
    }

    @Override
    public List<UserVerification> getVerificationDetails(Integer userId, String status, LocalDateTime startDate, LocalDateTime endDate) {
        // Create the CriteriaBuilder and CriteriaQuery
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserVerification> cq = cb.createQuery(UserVerification.class);

        // Define the root of the query
        Root<UserVerification> root = cq.from(UserVerification.class);

        // Prepare a list of predicates
        List<Predicate> predicates = new ArrayList<>();

        // Add predicate for userId
        if (userId != null) {
            predicates.add(cb.equal(root.get("userAccount").get("id"), userId));
        }

        // Add predicate for status
        if (status != null) {
            predicates.add(cb.equal(root.get("verificationStatus"), VerificationStatus.valueOf(status)));
        }

        // Add predicate for startDate
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("verifiedAt"), startDate));
        }

        // Add predicate for endDate
        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("verifiedAt"), endDate));
        }

        // Combine all predicates into a single where clause
        if (!predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        // Execute the query
        TypedQuery<UserVerification> query = entityManager.createQuery(cq);
        return query.getResultList();
    }


}
