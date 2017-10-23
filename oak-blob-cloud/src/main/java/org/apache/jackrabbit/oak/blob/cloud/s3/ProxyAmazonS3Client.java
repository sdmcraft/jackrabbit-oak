package org.apache.jackrabbit.oak.blob.cloud.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Request;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.Signer;
import com.amazonaws.handlers.RequestHandler;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.internal.auth.SignerProvider;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.S3ResponseMetadata;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by satyadeep on 10/23/17.
 */
public class ProxyAmazonS3Client extends AmazonS3Client{

    private static final Logger LOG = LoggerFactory.getLogger(ProxyAmazonS3Client.class);

    public ProxyAmazonS3Client() {
        super();
    }

    public ProxyAmazonS3Client(AWSCredentials awsCredentials) {
        super(awsCredentials);
    }

    public ProxyAmazonS3Client(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration) {
        super(awsCredentials, clientConfiguration);
    }

    public ProxyAmazonS3Client(AWSCredentialsProvider credentialsProvider) {
        super(credentialsProvider);
    }

    public ProxyAmazonS3Client(AWSCredentialsProvider credentialsProvider, ClientConfiguration clientConfiguration) {
        super(credentialsProvider, clientConfiguration);
    }

    public ProxyAmazonS3Client(AWSCredentialsProvider credentialsProvider, ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector) {
        super(credentialsProvider, clientConfiguration, requestMetricCollector);
    }

    public ProxyAmazonS3Client(ClientConfiguration clientConfiguration) {
        super(clientConfiguration);
    }

    @Override
    public synchronized void setEndpoint(String endpoint) {
        super.setEndpoint(endpoint);
    }

    @Override
    public synchronized void setRegion(Region region) {
        super.setRegion(region);
    }

    @Override
    public synchronized void setS3ClientOptions(S3ClientOptions clientOptions) {
        super.setS3ClientOptions(clientOptions);
    }

    @Override
    protected boolean useStrictHostNameVerification() {
        return super.useStrictHostNameVerification();
    }

    @Override
    public VersionListing listNextBatchOfVersions(VersionListing previousVersionListing) throws AmazonClientException, AmazonServiceException {
        return super.listNextBatchOfVersions(previousVersionListing);
    }

    @Override
    public VersionListing listNextBatchOfVersions(ListNextBatchOfVersionsRequest listNextBatchOfVersionsRequest) {
        return super.listNextBatchOfVersions(listNextBatchOfVersionsRequest);
    }

    @Override
    public VersionListing listVersions(String bucketName, String prefix) throws AmazonClientException, AmazonServiceException {
        return super.listVersions(bucketName, prefix);
    }

    @Override
    public VersionListing listVersions(String bucketName, String prefix, String keyMarker, String versionIdMarker, String delimiter, Integer maxKeys) throws AmazonClientException, AmazonServiceException {
        return super.listVersions(bucketName, prefix, keyMarker, versionIdMarker, delimiter, maxKeys);
    }

    @Override
    public VersionListing listVersions(ListVersionsRequest listVersionsRequest) throws AmazonClientException, AmazonServiceException {
        return super.listVersions(listVersionsRequest);
    }

    @Override
    public ObjectListing listObjects(String bucketName) throws AmazonClientException, AmazonServiceException {
        LOG.debug("listObjects {}", bucketName);
        return super.listObjects(bucketName);
    }

    @Override
    public ObjectListing listObjects(String bucketName, String prefix) throws AmazonClientException, AmazonServiceException {
        return super.listObjects(bucketName, prefix);
    }

    @Override
    public ObjectListing listObjects(ListObjectsRequest listObjectsRequest) throws AmazonClientException, AmazonServiceException {
        LOG.debug("listObjects {}", listObjectsRequest);
        return super.listObjects(listObjectsRequest);
    }

    @Override
    public ListObjectsV2Result listObjectsV2(String bucketName) throws AmazonClientException, AmazonServiceException {
        return super.listObjectsV2(bucketName);
    }

    @Override
    public ListObjectsV2Result listObjectsV2(String bucketName, String prefix) throws AmazonClientException, AmazonServiceException {
        return super.listObjectsV2(bucketName, prefix);
    }

    @Override
    public ListObjectsV2Result listObjectsV2(ListObjectsV2Request listObjectsV2Request) throws AmazonClientException, AmazonServiceException {
        return super.listObjectsV2(listObjectsV2Request);
    }

    @Override
    public ObjectListing listNextBatchOfObjects(ObjectListing previousObjectListing) throws AmazonClientException, AmazonServiceException {
        LOG.debug("listNextBatchOfObjects {}", previousObjectListing);
        return super.listNextBatchOfObjects(previousObjectListing);
    }

    @Override
    public ObjectListing listNextBatchOfObjects(ListNextBatchOfObjectsRequest listNextBatchOfObjectsRequest) throws AmazonClientException, AmazonServiceException {
        return super.listNextBatchOfObjects(listNextBatchOfObjectsRequest);
    }

    @Override
    public Owner getS3AccountOwner() throws AmazonClientException, AmazonServiceException {
        return super.getS3AccountOwner();
    }

    @Override
    public Owner getS3AccountOwner(GetS3AccountOwnerRequest getS3AccountOwnerRequest) throws AmazonClientException, AmazonServiceException {
        return super.getS3AccountOwner(getS3AccountOwnerRequest);
    }

    @Override
    public List<Bucket> listBuckets(ListBucketsRequest listBucketsRequest) throws AmazonClientException, AmazonServiceException {
        return super.listBuckets(listBucketsRequest);
    }

    @Override
    public List<Bucket> listBuckets() throws AmazonClientException, AmazonServiceException {
        return super.listBuckets();
    }

    @Override
    public String getBucketLocation(GetBucketLocationRequest getBucketLocationRequest) throws AmazonClientException, AmazonServiceException {
        return super.getBucketLocation(getBucketLocationRequest);
    }

    @Override
    public String getBucketLocation(String bucketName) throws AmazonClientException, AmazonServiceException {
        return super.getBucketLocation(bucketName);
    }

    @Override
    public Bucket createBucket(String bucketName) throws AmazonClientException, AmazonServiceException {
        return super.createBucket(bucketName);
    }

    @Override
    public Bucket createBucket(String bucketName, com.amazonaws.services.s3.model.Region region) throws AmazonClientException, AmazonServiceException {
        LOG.debug("createBucket {} {}", bucketName, region);
        return super.createBucket(bucketName, region);
    }

    @Override
    public Bucket createBucket(String bucketName, String region) throws AmazonClientException, AmazonServiceException {
        return super.createBucket(bucketName, region);
    }

    @Override
    public Bucket createBucket(CreateBucketRequest createBucketRequest) throws AmazonClientException, AmazonServiceException {
        return super.createBucket(createBucketRequest);
    }

    @Override
    public AccessControlList getObjectAcl(String bucketName, String key) throws AmazonClientException, AmazonServiceException {
        return super.getObjectAcl(bucketName, key);
    }

    @Override
    public AccessControlList getObjectAcl(String bucketName, String key, String versionId) throws AmazonClientException, AmazonServiceException {
        return super.getObjectAcl(bucketName, key, versionId);
    }

    @Override
    public AccessControlList getObjectAcl(GetObjectAclRequest getObjectAclRequest) {
        return super.getObjectAcl(getObjectAclRequest);
    }

    @Override
    public void setObjectAcl(String bucketName, String key, AccessControlList acl) throws AmazonClientException, AmazonServiceException {
        super.setObjectAcl(bucketName, key, acl);
    }

    @Override
    public void setObjectAcl(String bucketName, String key, CannedAccessControlList acl) throws AmazonClientException, AmazonServiceException {
        super.setObjectAcl(bucketName, key, acl);
    }

    @Override
    public void setObjectAcl(String bucketName, String key, String versionId, AccessControlList acl) throws AmazonClientException, AmazonServiceException {
        super.setObjectAcl(bucketName, key, versionId, acl);
    }

    @Override
    public void setObjectAcl(String bucketName, String key, String versionId, AccessControlList acl, RequestMetricCollector requestMetricCollector) throws AmazonClientException, AmazonServiceException {
        super.setObjectAcl(bucketName, key, versionId, acl, requestMetricCollector);
    }

    @Override
    public void setObjectAcl(String bucketName, String key, String versionId, CannedAccessControlList acl) throws AmazonClientException, AmazonServiceException {
        super.setObjectAcl(bucketName, key, versionId, acl);
    }

    @Override
    public void setObjectAcl(String bucketName, String key, String versionId, CannedAccessControlList acl, RequestMetricCollector requestMetricCollector) {
        super.setObjectAcl(bucketName, key, versionId, acl, requestMetricCollector);
    }

    @Override
    public void setObjectAcl(SetObjectAclRequest setObjectAclRequest) throws AmazonClientException, AmazonServiceException {
        super.setObjectAcl(setObjectAclRequest);
    }

    @Override
    public AccessControlList getBucketAcl(String bucketName) throws AmazonClientException, AmazonServiceException {
        return super.getBucketAcl(bucketName);
    }

    @Override
    public AccessControlList getBucketAcl(GetBucketAclRequest getBucketAclRequest) throws AmazonClientException, AmazonServiceException {
        return super.getBucketAcl(getBucketAclRequest);
    }

    @Override
    public void setBucketAcl(String bucketName, AccessControlList acl) throws AmazonClientException, AmazonServiceException {
        super.setBucketAcl(bucketName, acl);
    }

    @Override
    public void setBucketAcl(String bucketName, AccessControlList acl, RequestMetricCollector requestMetricCollector) {
        super.setBucketAcl(bucketName, acl, requestMetricCollector);
    }

    @Override
    public void setBucketAcl(String bucketName, CannedAccessControlList cannedAcl) throws AmazonClientException, AmazonServiceException {
        super.setBucketAcl(bucketName, cannedAcl);
    }

    @Override
    public void setBucketAcl(String bucketName, CannedAccessControlList cannedAcl, RequestMetricCollector requestMetricCollector) throws AmazonClientException, AmazonServiceException {
        super.setBucketAcl(bucketName, cannedAcl, requestMetricCollector);
    }

    @Override
    public void setBucketAcl(SetBucketAclRequest setBucketAclRequest) throws AmazonClientException, AmazonServiceException {
        super.setBucketAcl(setBucketAclRequest);
    }

    @Override
    public ObjectMetadata getObjectMetadata(String bucketName, String key) throws AmazonClientException, AmazonServiceException {
        LOG.debug("getObjectMetadata {} {}", bucketName, key);
        ObjectMetadata objectMetadata = super.getObjectMetadata(bucketName, key);
        LOG.info("Storage Class {} {} {}", bucketName, key, objectMetadata.getStorageClass());
        return objectMetadata;
    }

    @Override
    public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest) throws AmazonClientException, AmazonServiceException {
        return super.getObjectMetadata(getObjectMetadataRequest);
    }

    @Override
    public S3Object getObject(String bucketName, String key) throws AmazonClientException, AmazonServiceException {
        LOG.debug("getObject {} {}", bucketName, key);
        S3Object s3Object = super.getObject(bucketName, key);
        LOG.info("Storage Class {} {} {}", bucketName, key, s3Object.getObjectMetadata().getStorageClass());
        return s3Object;
    }

    @Override
    public boolean doesBucketExist(String bucketName) throws AmazonClientException, AmazonServiceException {
        LOG.debug("doesBucketExist {}", bucketName);
        return super.doesBucketExist(bucketName);
    }

    @Override
    public boolean doesObjectExist(String bucketName, String objectName) throws AmazonServiceException, AmazonClientException {
        return super.doesObjectExist(bucketName, objectName);
    }

    @Override
    public HeadBucketResult headBucket(HeadBucketRequest headBucketRequest) throws AmazonClientException, AmazonServiceException {
        return super.headBucket(headBucketRequest);
    }

    @Override
    public void changeObjectStorageClass(String bucketName, String key, StorageClass newStorageClass) throws AmazonClientException, AmazonServiceException {
        super.changeObjectStorageClass(bucketName, key, newStorageClass);
    }

    @Override
    public void setObjectRedirectLocation(String bucketName, String key, String newRedirectLocation) throws AmazonClientException, AmazonServiceException {
        super.setObjectRedirectLocation(bucketName, key, newRedirectLocation);
    }

    @Override
    public S3Object getObject(GetObjectRequest getObjectRequest) throws AmazonClientException, AmazonServiceException {
        return super.getObject(getObjectRequest);
    }

    @Override
    public ObjectMetadata getObject(GetObjectRequest getObjectRequest, File destinationFile) throws AmazonClientException, AmazonServiceException {
        return super.getObject(getObjectRequest, destinationFile);
    }

    @Override
    public String getObjectAsString(String bucketName, String key) throws AmazonServiceException, AmazonClientException {
        return super.getObjectAsString(bucketName, key);
    }

    @Override
    public void deleteBucket(String bucketName) throws AmazonClientException, AmazonServiceException {
        super.deleteBucket(bucketName);
    }

    @Override
    public void deleteBucket(DeleteBucketRequest deleteBucketRequest) throws AmazonClientException, AmazonServiceException {
        super.deleteBucket(deleteBucketRequest);
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, File file) throws AmazonClientException, AmazonServiceException {
        return super.putObject(bucketName, key, file);
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, InputStream input, ObjectMetadata metadata) throws AmazonClientException, AmazonServiceException {
        return super.putObject(bucketName, key, input, metadata);
    }

    @Override
    public PutObjectResult putObject(PutObjectRequest putObjectRequest) throws AmazonClientException, AmazonServiceException {
        return super.putObject(putObjectRequest);
    }

    @Override
    public CopyObjectResult copyObject(String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey) throws AmazonClientException, AmazonServiceException {
        return super.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
    }

    @Override
    public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest) throws AmazonClientException, AmazonServiceException {
        return super.copyObject(copyObjectRequest);
    }

    @Override
    public CopyPartResult copyPart(CopyPartRequest copyPartRequest) {
        return super.copyPart(copyPartRequest);
    }

    @Override
    public void deleteObject(String bucketName, String key) throws AmazonClientException, AmazonServiceException {
        LOG.debug("deleteObject {} {}", bucketName, key);
        super.deleteObject(bucketName, key);
    }

    @Override
    public void deleteObject(DeleteObjectRequest deleteObjectRequest) throws AmazonClientException, AmazonServiceException {
        super.deleteObject(deleteObjectRequest);
    }

    @Override
    public DeleteObjectsResult deleteObjects(DeleteObjectsRequest deleteObjectsRequest) {
        LOG.debug("deleteObjects {}", deleteObjectsRequest);
        return super.deleteObjects(deleteObjectsRequest);
    }

    @Override
    public void deleteVersion(String bucketName, String key, String versionId) throws AmazonClientException, AmazonServiceException {
        super.deleteVersion(bucketName, key, versionId);
    }

    @Override
    public void deleteVersion(DeleteVersionRequest deleteVersionRequest) throws AmazonClientException, AmazonServiceException {
        super.deleteVersion(deleteVersionRequest);
    }

    @Override
    public void setBucketVersioningConfiguration(SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        super.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);
    }

    @Override
    public BucketVersioningConfiguration getBucketVersioningConfiguration(String bucketName) throws AmazonClientException, AmazonServiceException {
        return super.getBucketVersioningConfiguration(bucketName);
    }

    @Override
    public BucketVersioningConfiguration getBucketVersioningConfiguration(GetBucketVersioningConfigurationRequest getBucketVersioningConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        return super.getBucketVersioningConfiguration(getBucketVersioningConfigurationRequest);
    }

    @Override
    public BucketWebsiteConfiguration getBucketWebsiteConfiguration(String bucketName) throws AmazonClientException, AmazonServiceException {
        return super.getBucketWebsiteConfiguration(bucketName);
    }

    @Override
    public BucketWebsiteConfiguration getBucketWebsiteConfiguration(GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        return super.getBucketWebsiteConfiguration(getBucketWebsiteConfigurationRequest);
    }

    @Override
    public BucketLifecycleConfiguration getBucketLifecycleConfiguration(String bucketName) {
        return super.getBucketLifecycleConfiguration(bucketName);
    }

    @Override
    public BucketLifecycleConfiguration getBucketLifecycleConfiguration(GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest) {
        return super.getBucketLifecycleConfiguration(getBucketLifecycleConfigurationRequest);
    }

    @Override
    public void setBucketLifecycleConfiguration(String bucketName, BucketLifecycleConfiguration bucketLifecycleConfiguration) {
        super.setBucketLifecycleConfiguration(bucketName, bucketLifecycleConfiguration);
    }

    @Override
    public void setBucketLifecycleConfiguration(SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest) {
        super.setBucketLifecycleConfiguration(setBucketLifecycleConfigurationRequest);
    }

    @Override
    public void deleteBucketLifecycleConfiguration(String bucketName) {
        super.deleteBucketLifecycleConfiguration(bucketName);
    }

    @Override
    public void deleteBucketLifecycleConfiguration(DeleteBucketLifecycleConfigurationRequest deleteBucketLifecycleConfigurationRequest) {
        super.deleteBucketLifecycleConfiguration(deleteBucketLifecycleConfigurationRequest);
    }

    @Override
    public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(String bucketName) {
        return super.getBucketCrossOriginConfiguration(bucketName);
    }

    @Override
    public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(GetBucketCrossOriginConfigurationRequest getBucketCrossOriginConfigurationRequest) {
        return super.getBucketCrossOriginConfiguration(getBucketCrossOriginConfigurationRequest);
    }

    @Override
    public void setBucketCrossOriginConfiguration(String bucketName, BucketCrossOriginConfiguration bucketCrossOriginConfiguration) {
        super.setBucketCrossOriginConfiguration(bucketName, bucketCrossOriginConfiguration);
    }

    @Override
    public void setBucketCrossOriginConfiguration(SetBucketCrossOriginConfigurationRequest setBucketCrossOriginConfigurationRequest) {
        super.setBucketCrossOriginConfiguration(setBucketCrossOriginConfigurationRequest);
    }

    @Override
    public void deleteBucketCrossOriginConfiguration(String bucketName) {
        super.deleteBucketCrossOriginConfiguration(bucketName);
    }

    @Override
    public void deleteBucketCrossOriginConfiguration(DeleteBucketCrossOriginConfigurationRequest deleteBucketCrossOriginConfigurationRequest) {
        super.deleteBucketCrossOriginConfiguration(deleteBucketCrossOriginConfigurationRequest);
    }

    @Override
    public BucketTaggingConfiguration getBucketTaggingConfiguration(String bucketName) {
        return super.getBucketTaggingConfiguration(bucketName);
    }

    @Override
    public BucketTaggingConfiguration getBucketTaggingConfiguration(GetBucketTaggingConfigurationRequest getBucketTaggingConfigurationRequest) {
        return super.getBucketTaggingConfiguration(getBucketTaggingConfigurationRequest);
    }

    @Override
    public void setBucketTaggingConfiguration(String bucketName, BucketTaggingConfiguration bucketTaggingConfiguration) {
        super.setBucketTaggingConfiguration(bucketName, bucketTaggingConfiguration);
    }

    @Override
    public void setBucketTaggingConfiguration(SetBucketTaggingConfigurationRequest setBucketTaggingConfigurationRequest) {
        super.setBucketTaggingConfiguration(setBucketTaggingConfigurationRequest);
    }

    @Override
    public void deleteBucketTaggingConfiguration(String bucketName) {
        super.deleteBucketTaggingConfiguration(bucketName);
    }

    @Override
    public void deleteBucketTaggingConfiguration(DeleteBucketTaggingConfigurationRequest deleteBucketTaggingConfigurationRequest) {
        super.deleteBucketTaggingConfiguration(deleteBucketTaggingConfigurationRequest);
    }

    @Override
    public void setBucketWebsiteConfiguration(String bucketName, BucketWebsiteConfiguration configuration) throws AmazonClientException, AmazonServiceException {
        super.setBucketWebsiteConfiguration(bucketName, configuration);
    }

    @Override
    public void setBucketWebsiteConfiguration(SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        super.setBucketWebsiteConfiguration(setBucketWebsiteConfigurationRequest);
    }

    @Override
    public void deleteBucketWebsiteConfiguration(String bucketName) throws AmazonClientException, AmazonServiceException {
        super.deleteBucketWebsiteConfiguration(bucketName);
    }

    @Override
    public void deleteBucketWebsiteConfiguration(DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        super.deleteBucketWebsiteConfiguration(deleteBucketWebsiteConfigurationRequest);
    }

    @Override
    public void setBucketNotificationConfiguration(String bucketName, BucketNotificationConfiguration bucketNotificationConfiguration) throws AmazonClientException, AmazonServiceException {
        super.setBucketNotificationConfiguration(bucketName, bucketNotificationConfiguration);
    }

    @Override
    public void setBucketNotificationConfiguration(SetBucketNotificationConfigurationRequest setBucketNotificationConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        super.setBucketNotificationConfiguration(setBucketNotificationConfigurationRequest);
    }

    @Override
    public BucketNotificationConfiguration getBucketNotificationConfiguration(String bucketName) throws AmazonClientException, AmazonServiceException {
        return super.getBucketNotificationConfiguration(bucketName);
    }

    @Override
    public BucketNotificationConfiguration getBucketNotificationConfiguration(GetBucketNotificationConfigurationRequest getBucketNotificationConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        return super.getBucketNotificationConfiguration(getBucketNotificationConfigurationRequest);
    }

    @Override
    public BucketLoggingConfiguration getBucketLoggingConfiguration(String bucketName) throws AmazonClientException, AmazonServiceException {
        return super.getBucketLoggingConfiguration(bucketName);
    }

    @Override
    public BucketLoggingConfiguration getBucketLoggingConfiguration(GetBucketLoggingConfigurationRequest getBucketLoggingConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        return super.getBucketLoggingConfiguration(getBucketLoggingConfigurationRequest);
    }

    @Override
    public void setBucketLoggingConfiguration(SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest) throws AmazonClientException, AmazonServiceException {
        super.setBucketLoggingConfiguration(setBucketLoggingConfigurationRequest);
    }

    @Override
    public BucketAccelerateConfiguration getBucketAccelerateConfiguration(String bucketName) throws AmazonServiceException, AmazonClientException {
        return super.getBucketAccelerateConfiguration(bucketName);
    }

    @Override
    public BucketAccelerateConfiguration getBucketAccelerateConfiguration(GetBucketAccelerateConfigurationRequest getBucketAccelerateConfigurationRequest) throws AmazonServiceException, AmazonClientException {
        return super.getBucketAccelerateConfiguration(getBucketAccelerateConfigurationRequest);
    }

    @Override
    public void setBucketAccelerateConfiguration(String bucketName, BucketAccelerateConfiguration accelerateConfiguration) throws AmazonServiceException, AmazonClientException {
        super.setBucketAccelerateConfiguration(bucketName, accelerateConfiguration);
    }

    @Override
    public void setBucketAccelerateConfiguration(SetBucketAccelerateConfigurationRequest setBucketAccelerateConfigurationRequest) throws AmazonServiceException, AmazonClientException {
        super.setBucketAccelerateConfiguration(setBucketAccelerateConfigurationRequest);
    }

    @Override
    public BucketPolicy getBucketPolicy(String bucketName) throws AmazonClientException, AmazonServiceException {
        return super.getBucketPolicy(bucketName);
    }

    @Override
    public void setBucketPolicy(String bucketName, String policyText) throws AmazonClientException, AmazonServiceException {
        super.setBucketPolicy(bucketName, policyText);
    }

    @Override
    public void deleteBucketPolicy(String bucketName) throws AmazonClientException, AmazonServiceException {
        super.deleteBucketPolicy(bucketName);
    }

    @Override
    public BucketPolicy getBucketPolicy(GetBucketPolicyRequest getBucketPolicyRequest) throws AmazonClientException, AmazonServiceException {
        return super.getBucketPolicy(getBucketPolicyRequest);
    }

    @Override
    public void setBucketPolicy(SetBucketPolicyRequest setBucketPolicyRequest) throws AmazonClientException, AmazonServiceException {
        super.setBucketPolicy(setBucketPolicyRequest);
    }

    @Override
    public void deleteBucketPolicy(DeleteBucketPolicyRequest deleteBucketPolicyRequest) throws AmazonClientException, AmazonServiceException {
        super.deleteBucketPolicy(deleteBucketPolicyRequest);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration) throws AmazonClientException {
        return super.generatePresignedUrl(bucketName, key, expiration);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethod method) throws AmazonClientException {
        return super.generatePresignedUrl(bucketName, key, expiration, method);
    }

    @Override
    public URL generatePresignedUrl(GeneratePresignedUrlRequest req) {
        return super.generatePresignedUrl(req);
    }

    @Override
    public void abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        super.abortMultipartUpload(abortMultipartUploadRequest);
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest completeMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        return super.completeMultipartUpload(completeMultipartUploadRequest);
    }

    @Override
    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest initiateMultipartUploadRequest) throws AmazonClientException, AmazonServiceException {
        return super.initiateMultipartUpload(initiateMultipartUploadRequest);
    }

    @Override
    public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest listMultipartUploadsRequest) throws AmazonClientException, AmazonServiceException {
        return super.listMultipartUploads(listMultipartUploadsRequest);
    }

    @Override
    public PartListing listParts(ListPartsRequest listPartsRequest) throws AmazonClientException, AmazonServiceException {
        return super.listParts(listPartsRequest);
    }

    @Override
    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) throws AmazonClientException, AmazonServiceException {
        return super.uploadPart(uploadPartRequest);
    }

    @Override
    public S3ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return super.getCachedResponseMetadata(request);
    }

    @Override
    public void restoreObject(RestoreObjectRequest restoreObjectRequest) throws AmazonServiceException {
        super.restoreObject(restoreObjectRequest);
    }

    @Override
    public void restoreObject(String bucketName, String key, int expirationInDays) throws AmazonServiceException {
        super.restoreObject(bucketName, key, expirationInDays);
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, String content) throws AmazonServiceException, AmazonClientException {
        return super.putObject(bucketName, key, content);
    }

    @Override
    protected Signer createSigner(Request<?> request, String bucketName, String key) {
        return super.createSigner(request, bucketName, key);
    }

    @Override
    protected <T> void presignRequest(Request<T> request, HttpMethod methodName, String bucketName, String key, Date expiration, String subResource) {
        super.presignRequest(request, methodName, bucketName, key, expiration, subResource);
    }

    @Override
    public String getResourceUrl(String bucketName, String key) {
        return super.getResourceUrl(bucketName, key);
    }

    @Override
    public URL getUrl(String bucketName, String key) {
        return super.getUrl(bucketName, key);
    }

    @Override
    public synchronized com.amazonaws.services.s3.model.Region getRegion() {
        return super.getRegion();
    }

    @Override
    protected <X extends AmazonWebServiceRequest> Request<X> createRequest(String bucketName, String key, X originalRequest, HttpMethodName httpMethod) {
        return super.createRequest(bucketName, key, originalRequest, httpMethod);
    }

    @Override
    protected <X extends AmazonWebServiceRequest> Request<X> createRequest(String bucketName, String key, X originalRequest, HttpMethodName httpMethod, URI endpoint) {
        return super.createRequest(bucketName, key, originalRequest, httpMethod, endpoint);
    }

    @Override
    public void enableRequesterPays(String bucketName) {
        super.enableRequesterPays(bucketName);
    }

    @Override
    public void disableRequesterPays(String bucketName) {
        super.disableRequesterPays(bucketName);
    }

    @Override
    public boolean isRequesterPaysEnabled(String bucketName) {
        return super.isRequesterPaysEnabled(bucketName);
    }

    @Override
    public void setBucketReplicationConfiguration(String bucketName, BucketReplicationConfiguration configuration) throws AmazonServiceException, AmazonClientException {
        super.setBucketReplicationConfiguration(bucketName, configuration);
    }

    @Override
    public void setBucketReplicationConfiguration(SetBucketReplicationConfigurationRequest setBucketReplicationConfigurationRequest) throws AmazonServiceException, AmazonClientException {
        super.setBucketReplicationConfiguration(setBucketReplicationConfigurationRequest);
    }

    @Override
    public BucketReplicationConfiguration getBucketReplicationConfiguration(String bucketName) throws AmazonServiceException, AmazonClientException {
        return super.getBucketReplicationConfiguration(bucketName);
    }

    @Override
    public BucketReplicationConfiguration getBucketReplicationConfiguration(GetBucketReplicationConfigurationRequest getBucketReplicationConfigurationRequest) throws AmazonServiceException, AmazonClientException {
        return super.getBucketReplicationConfiguration(getBucketReplicationConfigurationRequest);
    }

    @Override
    public void deleteBucketReplicationConfiguration(String bucketName) throws AmazonServiceException, AmazonClientException {
        super.deleteBucketReplicationConfiguration(bucketName);
    }

    @Override
    public void deleteBucketReplicationConfiguration(DeleteBucketReplicationConfigurationRequest deleteBucketReplicationConfigurationRequest) throws AmazonServiceException, AmazonClientException {
        super.deleteBucketReplicationConfiguration(deleteBucketReplicationConfigurationRequest);
    }

    @Override
    protected Signer getSigner() {
        return super.getSigner();
    }

    @Override
    public Signer getSignerByURI(URI uri) {
        return super.getSignerByURI(uri);
    }

    @Override
    public void shutdown() {
        LOG.debug("shutdown");
        super.shutdown();
    }

    @Override
    public void addRequestHandler(RequestHandler requestHandler) {
        super.addRequestHandler(requestHandler);
    }

    @Override
    public void addRequestHandler(RequestHandler2 requestHandler2) {
        super.addRequestHandler(requestHandler2);
    }

    @Override
    public void removeRequestHandler(RequestHandler requestHandler) {
        super.removeRequestHandler(requestHandler);
    }

    @Override
    public void removeRequestHandler(RequestHandler2 requestHandler2) {
        super.removeRequestHandler(requestHandler2);
    }

    @Override
    protected ExecutionContext createExecutionContext(AmazonWebServiceRequest req) {
        return super.createExecutionContext(req);
    }

    @Override
    protected ExecutionContext createExecutionContext(AmazonWebServiceRequest req, SignerProvider signerProvider) {
        return super.createExecutionContext(req, signerProvider);
    }

    @Override
    public void setTimeOffset(int timeOffset) {
        super.setTimeOffset(timeOffset);
    }

    @Override
    public AmazonWebServiceClient withTimeOffset(int timeOffset) {
        return super.withTimeOffset(timeOffset);
    }

    @Override
    public int getTimeOffset() {
        return super.getTimeOffset();
    }

    @Override
    public RequestMetricCollector getRequestMetricsCollector() {
        return super.getRequestMetricsCollector();
    }

    @Override
    protected RequestMetricCollector requestMetricCollector() {
        return super.requestMetricCollector();
    }

    @Override
    protected String getServiceAbbreviation() {
        return super.getServiceAbbreviation();
    }

    @Override
    public String getServiceName() {
        return super.getServiceName();
    }

    @Override
    public String getEndpointPrefix() {
        return super.getEndpointPrefix();
    }

    @Override
    protected void setEndpointPrefix(String endpointPrefix) {
        super.setEndpointPrefix(endpointPrefix);
    }

    @Override
    protected String getServiceNameIntern() {
        return super.getServiceNameIntern();
    }

    @Override
    public <T extends AmazonWebServiceClient> T withRegion(Region region) {
        return super.withRegion(region);
    }

    @Override
    public <T extends AmazonWebServiceClient> T withRegion(Regions region) {
        return super.withRegion(region);
    }

    @Override
    public <T extends AmazonWebServiceClient> T withEndpoint(String endpoint) {
        return super.withEndpoint(endpoint);
    }

    @Override
    protected boolean calculateCRC32FromCompressedData() {
        return super.calculateCRC32FromCompressedData();
    }
}
