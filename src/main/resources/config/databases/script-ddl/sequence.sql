CREATE SEQUENCE public.spring_user_seq
    INCREMENT 1
    START 1345
    MINVALUE 1345
    MAXVALUE 999999999999999999
    CACHE 1;

ALTER SEQUENCE public.spring_user_seq
    OWNER TO lemon;

CREATE SEQUENCE public.token_store_seq
    INCREMENT 1
    START 1345
    MINVALUE 1345
    MAXVALUE 999999999999999999
    CACHE 1;

ALTER SEQUENCE public.token_store_seq
    OWNER TO lemon;


CREATE SEQUENCE public.abstract_audit_seq
    INCREMENT 1
    START 1345
    MINVALUE 1345
    MAXVALUE 999999999999999999
    CACHE 1;

ALTER SEQUENCE public.abstract_audit_seq
    OWNER TO lemon;


SELECT last_value from spring_user_seq;
SELECT setval('spring_user_seq',nextval('spring_user_seq')+1023);
-- SELECT setval('spring_user_seq',1023);