package com.escrow.wazipay.media.dao;

import com.escrow.wazipay.media.entity.VerificationImage;
import java.util.List;

public interface VerificationImageDao {
    VerificationImage uploadImage(VerificationImage verificationImage);
    VerificationImage updateImage(VerificationImage verificationImage);

    VerificationImage getImageByImageId(Integer imageId);
    List<VerificationImage> getUserVerificationImages(Integer userId);
    String deleteImage(Integer imageId);
}
