package app.joybox.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AWSConfig {

    @Value("\${cloud.aws.accessKey}")
    lateinit var accessKey: String

    @Value("\${cloud.aws.secretKey}")
    lateinit var secretKey: String

    @Value("\${cloud.aws.region}")
    lateinit var region: String

    @Value("\${cloud.aws.endpoint}")
    lateinit var endpoint: String

    @Bean
    fun amazonS3(): AmazonS3 {
        val creds = BasicAWSCredentials(this.accessKey, this.secretKey)
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(this.endpoint, this.region))
            .withCredentials(AWSStaticCredentialsProvider(creds))
            .build()
    }
}