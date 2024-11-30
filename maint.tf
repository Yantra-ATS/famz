# Define the provider
provider "aws" {
  region = "us-east-2" # Change to your desired region
}

# Create an ECR repository
resource "aws_ecr_repository" "my_repository" {
  name                = "famz-ecr-repo"
  image_tag_mutability = "MUTABLE"
}

# Output repository URI
output "repository_url" {
  value = aws_ecr_repository.my_repository.repository_url
}

# Authenticate Docker to ECR (Optional)
data "aws_ecr_authorization_token" "token" {}

# Push Docker image to ECR (requires external tooling like AWS CLI or Docker CLI)
output "login_command" {
  value = "aws ecr get-login-password --region ${var.aws_region} | docker login --username AWS --password-stdin ${aws_ecr_repository.my_repository.repository_url}"
}

# Variables for AWS region (optional)
variable "aws_region" {
  default = "us-east-2"
}

# Create IAM Role for ECR Access
resource "aws_iam_role" "ecr_access_role" {
  name = "ecr-access-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com" # Or another service if required
        }
        Action = "sts:AssumeRole"
      }
    ]
  })
}

# Attach ECR policy to the IAM role
resource "aws_iam_role_policy" "ecr_policy" {
  name   = "ecr-policy"
  role   = aws_iam_role.ecr_access_role.id
  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        "Effect": "Allow",
        "Action": "ecr:GetAuthorizationToken",
        "Resource": "*"
      }
    ]
  })
}

# Output the IAM role ARN
output "iam_role_arn" {
  value = aws_iam_role.ecr_access_role.arn
}
