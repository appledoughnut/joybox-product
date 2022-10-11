package app.joybox.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
class AWSConfig {
    @Value("\${cloud.aws.region}")
    lateinit var region: String

    @Value("\${cloud.aws.endpoint}")
    lateinit var endpoint: String

    @Bean
    @Profile("local")
    fun amazonS3Local(): AmazonS3 {
        val creds = BasicAWSCredentials("", "")
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(this.endpoint, this.region))
            .withCredentials(AWSStaticCredentialsProvider(creds))
            .build()
    }

    @Bean
    @Profile("dev")
    fun amazonS3Dev(): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .build()
    }

    @Bean
    fun amazonS3(): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .build()
    }
}