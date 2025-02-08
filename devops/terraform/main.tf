terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.85"
    }
  }

  required_version = ">= 1.10.5"
}
provider "aws" {
  region = "eu-north-1"
}