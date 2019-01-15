##Security
    It's About how act my back-end security and it's rule.
    
##JSON Web Token Security
    authentication:
          maximum-live-session-count: 5
          maximum-login-limit-in-day: 200
          maximum-live-session-from-same-ip: 3
          jwt:
            secret: YjUzMWIzNjA5MmJjNDlmODZlMDFiM2ZlYzExNWJlZGFiMDNjMWZmMGUwZjFiNzYyMjExZjcyZDkxMGJiMDhiZmEyZTc2MTA0YWM0YjlmZDU1YzlkM2RlYWZjYWYwYjdiNDE3YmY5ZjBjYzQ2OWFmMDc1YzRjYzlhZjU5OGU3NmQ
            base64-secret: YjUzMWIzNjA5MmJjNDlmODZlMDFiM2ZlYzExNWJlZGFiMDNjMWZmMGUwZjFiNzYyMjExZjcyZDkxMGJiMDhiZmEyZTc2MTA0YWM0YjlmZDU1YzlkM2RlYWZjYWYwYjdiNDE3YmY5ZjBjYzQ2OWFmMDc1YzRjYzlhZjU5OGU3NmQ
            token-validity-in-seconds: 300
            token-validity-in-seconds-for-remember-me: 1800