--
-- PostgreSQL database dump
--

-- Dumped from database version 13.12 (Debian 13.12-1.pgdg120+1)
-- Dumped by pg_dump version 13.12 (Debian 13.12-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admin_event_entity; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.admin_event_entity (
    id character varying(36) NOT NULL,
    admin_event_time bigint,
    realm_id character varying(255),
    operation_type character varying(255),
    auth_realm_id character varying(255),
    auth_client_id character varying(255),
    auth_user_id character varying(255),
    ip_address character varying(255),
    resource_path character varying(2550),
    representation text,
    error character varying(255),
    resource_type character varying(64)
);


ALTER TABLE public.admin_event_entity OWNER TO dev_user;

--
-- Name: associated_policy; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.associated_policy (
    policy_id character varying(36) NOT NULL,
    associated_policy_id character varying(36) NOT NULL
);


ALTER TABLE public.associated_policy OWNER TO dev_user;

--
-- Name: authentication_execution; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.authentication_execution (
    id character varying(36) NOT NULL,
    alias character varying(255),
    authenticator character varying(36),
    realm_id character varying(36),
    flow_id character varying(36),
    requirement integer,
    priority integer,
    authenticator_flow boolean DEFAULT false NOT NULL,
    auth_flow_id character varying(36),
    auth_config character varying(36)
);


ALTER TABLE public.authentication_execution OWNER TO dev_user;

--
-- Name: authentication_flow; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.authentication_flow (
    id character varying(36) NOT NULL,
    alias character varying(255),
    description character varying(255),
    realm_id character varying(36),
    provider_id character varying(36) DEFAULT 'basic-flow'::character varying NOT NULL,
    top_level boolean DEFAULT false NOT NULL,
    built_in boolean DEFAULT false NOT NULL
);


ALTER TABLE public.authentication_flow OWNER TO dev_user;

--
-- Name: authenticator_config; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.authenticator_config (
    id character varying(36) NOT NULL,
    alias character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.authenticator_config OWNER TO dev_user;

--
-- Name: authenticator_config_entry; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.authenticator_config_entry (
    authenticator_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.authenticator_config_entry OWNER TO dev_user;

--
-- Name: broker_link; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.broker_link (
    identity_provider character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL,
    broker_user_id character varying(255),
    broker_username character varying(255),
    token text,
    user_id character varying(255) NOT NULL
);


ALTER TABLE public.broker_link OWNER TO dev_user;

--
-- Name: client; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client (
    id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    full_scope_allowed boolean DEFAULT false NOT NULL,
    client_id character varying(255),
    not_before integer,
    public_client boolean DEFAULT false NOT NULL,
    secret character varying(255),
    base_url character varying(255),
    bearer_only boolean DEFAULT false NOT NULL,
    management_url character varying(255),
    surrogate_auth_required boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    protocol character varying(255),
    node_rereg_timeout integer DEFAULT 0,
    frontchannel_logout boolean DEFAULT false NOT NULL,
    consent_required boolean DEFAULT false NOT NULL,
    name character varying(255),
    service_accounts_enabled boolean DEFAULT false NOT NULL,
    client_authenticator_type character varying(255),
    root_url character varying(255),
    description character varying(255),
    registration_token character varying(255),
    standard_flow_enabled boolean DEFAULT true NOT NULL,
    implicit_flow_enabled boolean DEFAULT false NOT NULL,
    direct_access_grants_enabled boolean DEFAULT false NOT NULL,
    always_display_in_console boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client OWNER TO dev_user;

--
-- Name: client_attributes; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_attributes (
    client_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.client_attributes OWNER TO dev_user;

--
-- Name: client_auth_flow_bindings; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_auth_flow_bindings (
    client_id character varying(36) NOT NULL,
    flow_id character varying(36),
    binding_name character varying(255) NOT NULL
);


ALTER TABLE public.client_auth_flow_bindings OWNER TO dev_user;

--
-- Name: client_initial_access; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_initial_access (
    id character varying(36) NOT NULL,
    realm_id character varying(36) NOT NULL,
    "timestamp" integer,
    expiration integer,
    count integer,
    remaining_count integer
);


ALTER TABLE public.client_initial_access OWNER TO dev_user;

--
-- Name: client_node_registrations; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_node_registrations (
    client_id character varying(36) NOT NULL,
    value integer,
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_node_registrations OWNER TO dev_user;

--
-- Name: client_scope; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_scope (
    id character varying(36) NOT NULL,
    name character varying(255),
    realm_id character varying(36),
    description character varying(255),
    protocol character varying(255)
);


ALTER TABLE public.client_scope OWNER TO dev_user;

--
-- Name: client_scope_attributes; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_scope_attributes (
    scope_id character varying(36) NOT NULL,
    value character varying(2048),
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_scope_attributes OWNER TO dev_user;

--
-- Name: client_scope_client; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_scope_client (
    client_id character varying(255) NOT NULL,
    scope_id character varying(255) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client_scope_client OWNER TO dev_user;

--
-- Name: client_scope_role_mapping; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_scope_role_mapping (
    scope_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.client_scope_role_mapping OWNER TO dev_user;

--
-- Name: client_session; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_session (
    id character varying(36) NOT NULL,
    client_id character varying(36),
    redirect_uri character varying(255),
    state character varying(255),
    "timestamp" integer,
    session_id character varying(36),
    auth_method character varying(255),
    realm_id character varying(255),
    auth_user_id character varying(36),
    current_action character varying(36)
);


ALTER TABLE public.client_session OWNER TO dev_user;

--
-- Name: client_session_auth_status; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_session_auth_status (
    authenticator character varying(36) NOT NULL,
    status integer,
    client_session character varying(36) NOT NULL
);


ALTER TABLE public.client_session_auth_status OWNER TO dev_user;

--
-- Name: client_session_note; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_session_note (
    name character varying(255) NOT NULL,
    value character varying(255),
    client_session character varying(36) NOT NULL
);


ALTER TABLE public.client_session_note OWNER TO dev_user;

--
-- Name: client_session_prot_mapper; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_session_prot_mapper (
    protocol_mapper_id character varying(36) NOT NULL,
    client_session character varying(36) NOT NULL
);


ALTER TABLE public.client_session_prot_mapper OWNER TO dev_user;

--
-- Name: client_session_role; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_session_role (
    role_id character varying(255) NOT NULL,
    client_session character varying(36) NOT NULL
);


ALTER TABLE public.client_session_role OWNER TO dev_user;

--
-- Name: client_user_session_note; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.client_user_session_note (
    name character varying(255) NOT NULL,
    value character varying(2048),
    client_session character varying(36) NOT NULL
);


ALTER TABLE public.client_user_session_note OWNER TO dev_user;

--
-- Name: component; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.component (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_id character varying(36),
    provider_id character varying(36),
    provider_type character varying(255),
    realm_id character varying(36),
    sub_type character varying(255)
);


ALTER TABLE public.component OWNER TO dev_user;

--
-- Name: component_config; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.component_config (
    id character varying(36) NOT NULL,
    component_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(4000)
);


ALTER TABLE public.component_config OWNER TO dev_user;

--
-- Name: composite_role; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.composite_role (
    composite character varying(36) NOT NULL,
    child_role character varying(36) NOT NULL
);


ALTER TABLE public.composite_role OWNER TO dev_user;

--
-- Name: credential; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    user_id character varying(36),
    created_date bigint,
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE public.credential OWNER TO dev_user;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO dev_user;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO dev_user;

--
-- Name: default_client_scope; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.default_client_scope (
    realm_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.default_client_scope OWNER TO dev_user;

--
-- Name: event_entity; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.event_entity (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    details_json character varying(2550),
    error character varying(255),
    ip_address character varying(255),
    realm_id character varying(255),
    session_id character varying(255),
    event_time bigint,
    type character varying(255),
    user_id character varying(255)
);


ALTER TABLE public.event_entity OWNER TO dev_user;

--
-- Name: fed_user_attribute; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.fed_user_attribute (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    value character varying(2024)
);


ALTER TABLE public.fed_user_attribute OWNER TO dev_user;

--
-- Name: fed_user_consent; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.fed_user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.fed_user_consent OWNER TO dev_user;

--
-- Name: fed_user_consent_cl_scope; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.fed_user_consent_cl_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.fed_user_consent_cl_scope OWNER TO dev_user;

--
-- Name: fed_user_credential; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.fed_user_credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    created_date bigint,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE public.fed_user_credential OWNER TO dev_user;

--
-- Name: fed_user_group_membership; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.fed_user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_group_membership OWNER TO dev_user;

--
-- Name: fed_user_required_action; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.fed_user_required_action (
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_required_action OWNER TO dev_user;

--
-- Name: fed_user_role_mapping; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.fed_user_role_mapping (
    role_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_role_mapping OWNER TO dev_user;

--
-- Name: federated_identity; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.federated_identity (
    identity_provider character varying(255) NOT NULL,
    realm_id character varying(36),
    federated_user_id character varying(255),
    federated_username character varying(255),
    token text,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_identity OWNER TO dev_user;

--
-- Name: federated_user; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.federated_user (
    id character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_user OWNER TO dev_user;

--
-- Name: group_attribute; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.group_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_attribute OWNER TO dev_user;

--
-- Name: group_role_mapping; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.group_role_mapping (
    role_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_role_mapping OWNER TO dev_user;

--
-- Name: identity_provider; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.identity_provider (
    internal_id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    provider_alias character varying(255),
    provider_id character varying(255),
    store_token boolean DEFAULT false NOT NULL,
    authenticate_by_default boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    add_token_role boolean DEFAULT true NOT NULL,
    trust_email boolean DEFAULT false NOT NULL,
    first_broker_login_flow_id character varying(36),
    post_broker_login_flow_id character varying(36),
    provider_display_name character varying(255),
    link_only boolean DEFAULT false NOT NULL
);


ALTER TABLE public.identity_provider OWNER TO dev_user;

--
-- Name: identity_provider_config; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.identity_provider_config (
    identity_provider_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.identity_provider_config OWNER TO dev_user;

--
-- Name: identity_provider_mapper; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.identity_provider_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    idp_alias character varying(255) NOT NULL,
    idp_mapper_name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.identity_provider_mapper OWNER TO dev_user;

--
-- Name: idp_mapper_config; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.idp_mapper_config (
    idp_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.idp_mapper_config OWNER TO dev_user;

--
-- Name: keycloak_group; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.keycloak_group (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_group character varying(36) NOT NULL,
    realm_id character varying(36)
);


ALTER TABLE public.keycloak_group OWNER TO dev_user;

--
-- Name: keycloak_role; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.keycloak_role (
    id character varying(36) NOT NULL,
    client_realm_constraint character varying(255),
    client_role boolean DEFAULT false NOT NULL,
    description character varying(255),
    name character varying(255),
    realm_id character varying(255),
    client character varying(36),
    realm character varying(36)
);


ALTER TABLE public.keycloak_role OWNER TO dev_user;

--
-- Name: migration_model; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.migration_model (
    id character varying(36) NOT NULL,
    version character varying(36),
    update_time bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.migration_model OWNER TO dev_user;

--
-- Name: offline_client_session; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.offline_client_session (
    user_session_id character varying(36) NOT NULL,
    client_id character varying(255) NOT NULL,
    offline_flag character varying(4) NOT NULL,
    "timestamp" integer,
    data text,
    client_storage_provider character varying(36) DEFAULT 'local'::character varying NOT NULL,
    external_client_id character varying(255) DEFAULT 'local'::character varying NOT NULL
);


ALTER TABLE public.offline_client_session OWNER TO dev_user;

--
-- Name: offline_user_session; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.offline_user_session (
    user_session_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    created_on integer NOT NULL,
    offline_flag character varying(4) NOT NULL,
    data text,
    last_session_refresh integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.offline_user_session OWNER TO dev_user;

--
-- Name: policy_config; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.policy_config (
    policy_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.policy_config OWNER TO dev_user;

--
-- Name: protocol_mapper; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.protocol_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    protocol character varying(255) NOT NULL,
    protocol_mapper_name character varying(255) NOT NULL,
    client_id character varying(36),
    client_scope_id character varying(36)
);


ALTER TABLE public.protocol_mapper OWNER TO dev_user;

--
-- Name: protocol_mapper_config; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.protocol_mapper_config (
    protocol_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.protocol_mapper_config OWNER TO dev_user;

--
-- Name: realm; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.realm (
    id character varying(36) NOT NULL,
    access_code_lifespan integer,
    user_action_lifespan integer,
    access_token_lifespan integer,
    account_theme character varying(255),
    admin_theme character varying(255),
    email_theme character varying(255),
    enabled boolean DEFAULT false NOT NULL,
    events_enabled boolean DEFAULT false NOT NULL,
    events_expiration bigint,
    login_theme character varying(255),
    name character varying(255),
    not_before integer,
    password_policy character varying(2550),
    registration_allowed boolean DEFAULT false NOT NULL,
    remember_me boolean DEFAULT false NOT NULL,
    reset_password_allowed boolean DEFAULT false NOT NULL,
    social boolean DEFAULT false NOT NULL,
    ssl_required character varying(255),
    sso_idle_timeout integer,
    sso_max_lifespan integer,
    update_profile_on_soc_login boolean DEFAULT false NOT NULL,
    verify_email boolean DEFAULT false NOT NULL,
    master_admin_client character varying(36),
    login_lifespan integer,
    internationalization_enabled boolean DEFAULT false NOT NULL,
    default_locale character varying(255),
    reg_email_as_username boolean DEFAULT false NOT NULL,
    admin_events_enabled boolean DEFAULT false NOT NULL,
    admin_events_details_enabled boolean DEFAULT false NOT NULL,
    edit_username_allowed boolean DEFAULT false NOT NULL,
    otp_policy_counter integer DEFAULT 0,
    otp_policy_window integer DEFAULT 1,
    otp_policy_period integer DEFAULT 30,
    otp_policy_digits integer DEFAULT 6,
    otp_policy_alg character varying(36) DEFAULT 'HmacSHA1'::character varying,
    otp_policy_type character varying(36) DEFAULT 'totp'::character varying,
    browser_flow character varying(36),
    registration_flow character varying(36),
    direct_grant_flow character varying(36),
    reset_credentials_flow character varying(36),
    client_auth_flow character varying(36),
    offline_session_idle_timeout integer DEFAULT 0,
    revoke_refresh_token boolean DEFAULT false NOT NULL,
    access_token_life_implicit integer DEFAULT 0,
    login_with_email_allowed boolean DEFAULT true NOT NULL,
    duplicate_emails_allowed boolean DEFAULT false NOT NULL,
    docker_auth_flow character varying(36),
    refresh_token_max_reuse integer DEFAULT 0,
    allow_user_managed_access boolean DEFAULT false NOT NULL,
    sso_max_lifespan_remember_me integer DEFAULT 0 NOT NULL,
    sso_idle_timeout_remember_me integer DEFAULT 0 NOT NULL,
    default_role character varying(255)
);


ALTER TABLE public.realm OWNER TO dev_user;

--
-- Name: realm_attribute; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.realm_attribute (
    name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    value text
);


ALTER TABLE public.realm_attribute OWNER TO dev_user;

--
-- Name: realm_default_groups; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.realm_default_groups (
    realm_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_default_groups OWNER TO dev_user;

--
-- Name: realm_enabled_event_types; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.realm_enabled_event_types (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_enabled_event_types OWNER TO dev_user;

--
-- Name: realm_events_listeners; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.realm_events_listeners (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_events_listeners OWNER TO dev_user;

--
-- Name: realm_localizations; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.realm_localizations (
    realm_id character varying(255) NOT NULL,
    locale character varying(255) NOT NULL,
    texts text NOT NULL
);


ALTER TABLE public.realm_localizations OWNER TO dev_user;

--
-- Name: realm_required_credential; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.realm_required_credential (
    type character varying(255) NOT NULL,
    form_label character varying(255),
    input boolean DEFAULT false NOT NULL,
    secret boolean DEFAULT false NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_required_credential OWNER TO dev_user;

--
-- Name: realm_smtp_config; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.realm_smtp_config (
    realm_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.realm_smtp_config OWNER TO dev_user;

--
-- Name: realm_supported_locales; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.realm_supported_locales (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_supported_locales OWNER TO dev_user;

--
-- Name: redirect_uris; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.redirect_uris (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.redirect_uris OWNER TO dev_user;

--
-- Name: required_action_config; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.required_action_config (
    required_action_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.required_action_config OWNER TO dev_user;

--
-- Name: required_action_provider; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.required_action_provider (
    id character varying(36) NOT NULL,
    alias character varying(255),
    name character varying(255),
    realm_id character varying(36),
    enabled boolean DEFAULT false NOT NULL,
    default_action boolean DEFAULT false NOT NULL,
    provider_id character varying(255),
    priority integer
);


ALTER TABLE public.required_action_provider OWNER TO dev_user;

--
-- Name: resource_attribute; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.resource_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    resource_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_attribute OWNER TO dev_user;

--
-- Name: resource_policy; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.resource_policy (
    resource_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_policy OWNER TO dev_user;

--
-- Name: resource_scope; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.resource_scope (
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_scope OWNER TO dev_user;

--
-- Name: resource_server; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.resource_server (
    id character varying(36) NOT NULL,
    allow_rs_remote_mgmt boolean DEFAULT false NOT NULL,
    policy_enforce_mode smallint NOT NULL,
    decision_strategy smallint DEFAULT 1 NOT NULL
);


ALTER TABLE public.resource_server OWNER TO dev_user;

--
-- Name: resource_server_perm_ticket; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.resource_server_perm_ticket (
    id character varying(36) NOT NULL,
    owner character varying(255) NOT NULL,
    requester character varying(255) NOT NULL,
    created_timestamp bigint NOT NULL,
    granted_timestamp bigint,
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36),
    resource_server_id character varying(36) NOT NULL,
    policy_id character varying(36)
);


ALTER TABLE public.resource_server_perm_ticket OWNER TO dev_user;

--
-- Name: resource_server_policy; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.resource_server_policy (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    type character varying(255) NOT NULL,
    decision_strategy smallint,
    logic smallint,
    resource_server_id character varying(36) NOT NULL,
    owner character varying(255)
);


ALTER TABLE public.resource_server_policy OWNER TO dev_user;

--
-- Name: resource_server_resource; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.resource_server_resource (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(255),
    icon_uri character varying(255),
    owner character varying(255) NOT NULL,
    resource_server_id character varying(36) NOT NULL,
    owner_managed_access boolean DEFAULT false NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_resource OWNER TO dev_user;

--
-- Name: resource_server_scope; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.resource_server_scope (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    icon_uri character varying(255),
    resource_server_id character varying(36) NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_scope OWNER TO dev_user;

--
-- Name: resource_uris; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.resource_uris (
    resource_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.resource_uris OWNER TO dev_user;

--
-- Name: role_attribute; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.role_attribute (
    id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255)
);


ALTER TABLE public.role_attribute OWNER TO dev_user;

--
-- Name: scope_mapping; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.scope_mapping (
    client_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_mapping OWNER TO dev_user;

--
-- Name: scope_policy; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.scope_policy (
    scope_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_policy OWNER TO dev_user;

--
-- Name: user_attribute; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_attribute (
    name character varying(255) NOT NULL,
    value character varying(255),
    user_id character varying(36) NOT NULL,
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL
);


ALTER TABLE public.user_attribute OWNER TO dev_user;

--
-- Name: user_consent; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(36) NOT NULL,
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.user_consent OWNER TO dev_user;

--
-- Name: user_consent_client_scope; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_consent_client_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.user_consent_client_scope OWNER TO dev_user;

--
-- Name: user_entity; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_entity (
    id character varying(36) NOT NULL,
    email character varying(255),
    email_constraint character varying(255),
    email_verified boolean DEFAULT false NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    federation_link character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    realm_id character varying(255),
    username character varying(255),
    created_timestamp bigint,
    service_account_client_link character varying(255),
    not_before integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.user_entity OWNER TO dev_user;

--
-- Name: user_federation_config; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_federation_config (
    user_federation_provider_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_config OWNER TO dev_user;

--
-- Name: user_federation_mapper; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_federation_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    federation_provider_id character varying(36) NOT NULL,
    federation_mapper_type character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.user_federation_mapper OWNER TO dev_user;

--
-- Name: user_federation_mapper_config; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_federation_mapper_config (
    user_federation_mapper_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_mapper_config OWNER TO dev_user;

--
-- Name: user_federation_provider; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_federation_provider (
    id character varying(36) NOT NULL,
    changed_sync_period integer,
    display_name character varying(255),
    full_sync_period integer,
    last_sync integer,
    priority integer,
    provider_name character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.user_federation_provider OWNER TO dev_user;

--
-- Name: user_group_membership; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.user_group_membership OWNER TO dev_user;

--
-- Name: user_required_action; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_required_action (
    user_id character varying(36) NOT NULL,
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL
);


ALTER TABLE public.user_required_action OWNER TO dev_user;

--
-- Name: user_role_mapping; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_role_mapping (
    role_id character varying(255) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.user_role_mapping OWNER TO dev_user;

--
-- Name: user_session; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_session (
    id character varying(36) NOT NULL,
    auth_method character varying(255),
    ip_address character varying(255),
    last_session_refresh integer,
    login_username character varying(255),
    realm_id character varying(255),
    remember_me boolean DEFAULT false NOT NULL,
    started integer,
    user_id character varying(255),
    user_session_state integer,
    broker_session_id character varying(255),
    broker_user_id character varying(255)
);


ALTER TABLE public.user_session OWNER TO dev_user;

--
-- Name: user_session_note; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.user_session_note (
    user_session character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(2048)
);


ALTER TABLE public.user_session_note OWNER TO dev_user;

--
-- Name: username_login_failure; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.username_login_failure (
    realm_id character varying(36) NOT NULL,
    username character varying(255) NOT NULL,
    failed_login_not_before integer,
    last_failure bigint,
    last_ip_failure character varying(255),
    num_failures integer
);


ALTER TABLE public.username_login_failure OWNER TO dev_user;

--
-- Name: web_origins; Type: TABLE; Schema: public; Owner: dev_user
--

CREATE TABLE public.web_origins (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.web_origins OWNER TO dev_user;

--
-- Data for Name: admin_event_entity; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.admin_event_entity (id, admin_event_time, realm_id, operation_type, auth_realm_id, auth_client_id, auth_user_id, ip_address, resource_path, representation, error, resource_type) FROM stdin;
\.


--
-- Data for Name: associated_policy; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.associated_policy (policy_id, associated_policy_id) FROM stdin;
\.


--
-- Data for Name: authentication_execution; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.authentication_execution (id, alias, authenticator, realm_id, flow_id, requirement, priority, authenticator_flow, auth_flow_id, auth_config) FROM stdin;
f175d3d9-0865-4596-87ec-1e960bbb4d16	\N	auth-cookie	fc3e583c-0722-4ee1-aeef-70af67cf78ba	34851a15-ddd8-47e2-8b49-6a04d4d64d89	2	10	f	\N	\N
32556f2a-5f5a-411c-85c1-11bbe054e62c	\N	auth-spnego	fc3e583c-0722-4ee1-aeef-70af67cf78ba	34851a15-ddd8-47e2-8b49-6a04d4d64d89	3	20	f	\N	\N
c7af4503-f293-4934-bfb0-3f65308e8afd	\N	identity-provider-redirector	fc3e583c-0722-4ee1-aeef-70af67cf78ba	34851a15-ddd8-47e2-8b49-6a04d4d64d89	2	25	f	\N	\N
6fd48b6a-924e-4ee6-bdb9-6e51879b1b1a	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	34851a15-ddd8-47e2-8b49-6a04d4d64d89	2	30	t	d981a4f6-d2ea-495a-9d01-dbbf670a0774	\N
c632bb81-1f07-413b-a2ab-306440363698	\N	auth-username-password-form	fc3e583c-0722-4ee1-aeef-70af67cf78ba	d981a4f6-d2ea-495a-9d01-dbbf670a0774	0	10	f	\N	\N
7ac5db4c-9c53-448e-9fb0-975a37dc0dd3	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	d981a4f6-d2ea-495a-9d01-dbbf670a0774	1	20	t	851d7bea-1637-47d4-8feb-c826417b06a2	\N
5dc0a025-3eec-44da-9d38-0f2446f31ccb	\N	conditional-user-configured	fc3e583c-0722-4ee1-aeef-70af67cf78ba	851d7bea-1637-47d4-8feb-c826417b06a2	0	10	f	\N	\N
671b7a13-97c4-42ad-980e-31b703a2ab91	\N	auth-otp-form	fc3e583c-0722-4ee1-aeef-70af67cf78ba	851d7bea-1637-47d4-8feb-c826417b06a2	0	20	f	\N	\N
c8f4c6b0-e936-47a5-8bc0-f2ba3d548c98	\N	direct-grant-validate-username	fc3e583c-0722-4ee1-aeef-70af67cf78ba	3f198a37-b5ed-4b11-8847-d1937a4fc56e	0	10	f	\N	\N
6b7d44f1-65d3-4feb-a975-8f1329d1f11e	\N	direct-grant-validate-password	fc3e583c-0722-4ee1-aeef-70af67cf78ba	3f198a37-b5ed-4b11-8847-d1937a4fc56e	0	20	f	\N	\N
420d4868-c72e-4454-aa02-02a71f5f2813	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	3f198a37-b5ed-4b11-8847-d1937a4fc56e	1	30	t	69b2efe2-ee2a-4e6c-b8c9-2b6d40d9af96	\N
c6c428ad-1aa9-4f0b-b8af-656c21f798af	\N	conditional-user-configured	fc3e583c-0722-4ee1-aeef-70af67cf78ba	69b2efe2-ee2a-4e6c-b8c9-2b6d40d9af96	0	10	f	\N	\N
9553fce8-f933-409c-a831-f43660084293	\N	direct-grant-validate-otp	fc3e583c-0722-4ee1-aeef-70af67cf78ba	69b2efe2-ee2a-4e6c-b8c9-2b6d40d9af96	0	20	f	\N	\N
8178a23e-d53f-4acd-830c-4689ced490b9	\N	registration-page-form	fc3e583c-0722-4ee1-aeef-70af67cf78ba	81cefadf-789e-4571-8c51-a3fba9e5857c	0	10	t	4ee1d709-b916-4841-a9fe-a861946040fa	\N
297565e2-ce3f-4fc5-bac7-81beab964aef	\N	registration-user-creation	fc3e583c-0722-4ee1-aeef-70af67cf78ba	4ee1d709-b916-4841-a9fe-a861946040fa	0	20	f	\N	\N
7a979fcf-9eb7-4688-abac-641437957bfb	\N	registration-profile-action	fc3e583c-0722-4ee1-aeef-70af67cf78ba	4ee1d709-b916-4841-a9fe-a861946040fa	0	40	f	\N	\N
52106fb7-997e-439b-a353-98742c6ab994	\N	registration-password-action	fc3e583c-0722-4ee1-aeef-70af67cf78ba	4ee1d709-b916-4841-a9fe-a861946040fa	0	50	f	\N	\N
4f0d219f-6d81-4e05-ae21-a61a0b7e15cd	\N	registration-recaptcha-action	fc3e583c-0722-4ee1-aeef-70af67cf78ba	4ee1d709-b916-4841-a9fe-a861946040fa	3	60	f	\N	\N
2f227c94-f5e5-4838-aaa0-4869571cc026	\N	reset-credentials-choose-user	fc3e583c-0722-4ee1-aeef-70af67cf78ba	b51ea154-95e5-4ec4-9547-a74a9f5d36f9	0	10	f	\N	\N
507b2e89-e8e9-45f4-a049-d71ffdd35ff7	\N	reset-credential-email	fc3e583c-0722-4ee1-aeef-70af67cf78ba	b51ea154-95e5-4ec4-9547-a74a9f5d36f9	0	20	f	\N	\N
dc30f402-cbaf-43f3-a26f-177b95463470	\N	reset-password	fc3e583c-0722-4ee1-aeef-70af67cf78ba	b51ea154-95e5-4ec4-9547-a74a9f5d36f9	0	30	f	\N	\N
6afa6ff9-87e0-4f34-a2e0-cded4cb557c7	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	b51ea154-95e5-4ec4-9547-a74a9f5d36f9	1	40	t	198afb3d-8c4f-488c-a264-a88c17fd0fe2	\N
1524b4c5-1543-4629-80d5-3661a163a438	\N	conditional-user-configured	fc3e583c-0722-4ee1-aeef-70af67cf78ba	198afb3d-8c4f-488c-a264-a88c17fd0fe2	0	10	f	\N	\N
ac5a92b5-c9e0-43c2-ad62-783fd929e43c	\N	reset-otp	fc3e583c-0722-4ee1-aeef-70af67cf78ba	198afb3d-8c4f-488c-a264-a88c17fd0fe2	0	20	f	\N	\N
754a2386-862e-4648-affa-7a997a443a59	\N	client-secret	fc3e583c-0722-4ee1-aeef-70af67cf78ba	eb100bd9-a3b0-411a-b806-897af36a7bbc	2	10	f	\N	\N
3c250014-16dd-487a-af94-6afeafe822a7	\N	client-jwt	fc3e583c-0722-4ee1-aeef-70af67cf78ba	eb100bd9-a3b0-411a-b806-897af36a7bbc	2	20	f	\N	\N
341a4e8e-4b19-456e-a81e-7e311011fc46	\N	client-secret-jwt	fc3e583c-0722-4ee1-aeef-70af67cf78ba	eb100bd9-a3b0-411a-b806-897af36a7bbc	2	30	f	\N	\N
7a43c2e0-1666-4249-9fe9-4bdb203a0372	\N	client-x509	fc3e583c-0722-4ee1-aeef-70af67cf78ba	eb100bd9-a3b0-411a-b806-897af36a7bbc	2	40	f	\N	\N
49422aee-f4eb-4961-9ab0-4e8fd111035e	\N	idp-review-profile	fc3e583c-0722-4ee1-aeef-70af67cf78ba	a9594579-18d4-4176-8ec7-03c2c89c0cd3	0	10	f	\N	0ff9d931-cb83-4dca-9087-629c21a04935
1b07efe8-274c-418d-adc8-dadbfd3ed505	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	a9594579-18d4-4176-8ec7-03c2c89c0cd3	0	20	t	a75a13d2-e1ab-4ad1-b27b-1c24cdb5e38b	\N
40ccdc59-17ee-4acc-88f4-e971637b1242	\N	idp-create-user-if-unique	fc3e583c-0722-4ee1-aeef-70af67cf78ba	a75a13d2-e1ab-4ad1-b27b-1c24cdb5e38b	2	10	f	\N	4ea3bf9e-7a76-442a-8681-5a89af09321d
708ab95f-bf92-4181-8ec3-9d2b0c526589	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	a75a13d2-e1ab-4ad1-b27b-1c24cdb5e38b	2	20	t	f43a18a5-f738-4ae6-8cb4-4550c3bf089b	\N
5dbfd3dc-03ac-4709-bb71-8382df7c1d5a	\N	idp-confirm-link	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f43a18a5-f738-4ae6-8cb4-4550c3bf089b	0	10	f	\N	\N
5fbf6cab-8c0b-420b-9eae-7b45e3ea6fff	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f43a18a5-f738-4ae6-8cb4-4550c3bf089b	0	20	t	45a275d0-ed07-4fc0-994d-39ad9ee2d102	\N
24310811-9e49-41c8-bbc0-1c6d51231430	\N	idp-email-verification	fc3e583c-0722-4ee1-aeef-70af67cf78ba	45a275d0-ed07-4fc0-994d-39ad9ee2d102	2	10	f	\N	\N
ac80e499-109e-4806-ba25-a24049885516	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	45a275d0-ed07-4fc0-994d-39ad9ee2d102	2	20	t	061baee4-a666-4ba4-8362-456613c679c4	\N
ff1ffc99-b812-456e-bdb9-88cdfdab4ce5	\N	idp-username-password-form	fc3e583c-0722-4ee1-aeef-70af67cf78ba	061baee4-a666-4ba4-8362-456613c679c4	0	10	f	\N	\N
7cf1bb3f-7eb7-47e0-846f-dc23b48bffc7	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	061baee4-a666-4ba4-8362-456613c679c4	1	20	t	0677a714-d7b9-4c2a-b7f5-8ced77e6702a	\N
a0c1d4a2-c97e-4ef3-ae7a-0bff8107ecac	\N	conditional-user-configured	fc3e583c-0722-4ee1-aeef-70af67cf78ba	0677a714-d7b9-4c2a-b7f5-8ced77e6702a	0	10	f	\N	\N
19650f5a-0732-455c-9044-904224046f89	\N	auth-otp-form	fc3e583c-0722-4ee1-aeef-70af67cf78ba	0677a714-d7b9-4c2a-b7f5-8ced77e6702a	0	20	f	\N	\N
180cd7f6-a8e8-4d06-933b-5a4479fe53ea	\N	http-basic-authenticator	fc3e583c-0722-4ee1-aeef-70af67cf78ba	c848bf58-8bdb-43f3-954b-a2b3d0ef2dfb	0	10	f	\N	\N
c72b78bc-59e1-4fd0-81a2-6f9cf1cd8db9	\N	docker-http-basic-authenticator	fc3e583c-0722-4ee1-aeef-70af67cf78ba	1206965f-dcbd-4871-a2a3-b26b59971301	0	10	f	\N	\N
4b26dcd7-77e1-4553-9b05-ac8986e8ac1d	\N	no-cookie-redirect	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f5fb3bf1-c145-4c15-86bb-9442c62f39bf	0	10	f	\N	\N
ef219422-f595-407b-9657-8c76ddb4b2d5	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f5fb3bf1-c145-4c15-86bb-9442c62f39bf	0	20	t	299f2afd-6a86-4d56-ac98-2318c761bf77	\N
1a8cfe19-2966-490e-b94c-0e8606ffc96f	\N	basic-auth	fc3e583c-0722-4ee1-aeef-70af67cf78ba	299f2afd-6a86-4d56-ac98-2318c761bf77	0	10	f	\N	\N
29df7f6d-757d-485c-8eec-f89aab4a21f9	\N	basic-auth-otp	fc3e583c-0722-4ee1-aeef-70af67cf78ba	299f2afd-6a86-4d56-ac98-2318c761bf77	3	20	f	\N	\N
76304969-67a7-4418-894e-8a84675cdc27	\N	auth-spnego	fc3e583c-0722-4ee1-aeef-70af67cf78ba	299f2afd-6a86-4d56-ac98-2318c761bf77	3	30	f	\N	\N
725b8819-1037-4de0-a77e-143f9497b6d9	\N	auth-cookie	89f9614c-27fd-44d6-b07c-c527669d1d5c	9d711a7a-1fc5-4553-88ad-a11a4c1115b3	2	10	f	\N	\N
6efe6d13-9d58-4b67-b934-5a765ca78f94	\N	auth-spnego	89f9614c-27fd-44d6-b07c-c527669d1d5c	9d711a7a-1fc5-4553-88ad-a11a4c1115b3	3	20	f	\N	\N
efcc5ec3-4909-4722-8bbb-faf8eddb9de1	\N	identity-provider-redirector	89f9614c-27fd-44d6-b07c-c527669d1d5c	9d711a7a-1fc5-4553-88ad-a11a4c1115b3	2	25	f	\N	\N
086f9b7d-44f6-4c5e-897c-d832e6abcea8	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	9d711a7a-1fc5-4553-88ad-a11a4c1115b3	2	30	t	d9dde025-1e16-46ff-a623-e0d5e8d74f3b	\N
297df51a-2a73-40d9-878d-16150d2e92d7	\N	auth-username-password-form	89f9614c-27fd-44d6-b07c-c527669d1d5c	d9dde025-1e16-46ff-a623-e0d5e8d74f3b	0	10	f	\N	\N
6a3757d1-36ec-4dbb-b5e8-8726bac1e35e	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	d9dde025-1e16-46ff-a623-e0d5e8d74f3b	1	20	t	5ae0e3e1-28ab-4c59-b851-1a877b685cd6	\N
5f3ab577-c5c4-42af-987f-8687ec06a5c9	\N	conditional-user-configured	89f9614c-27fd-44d6-b07c-c527669d1d5c	5ae0e3e1-28ab-4c59-b851-1a877b685cd6	0	10	f	\N	\N
986b6d2e-ce1d-4f0b-934c-d03f5fe1f5e9	\N	auth-otp-form	89f9614c-27fd-44d6-b07c-c527669d1d5c	5ae0e3e1-28ab-4c59-b851-1a877b685cd6	0	20	f	\N	\N
1b00a35c-14bf-4e8e-9257-e71a2d8e663d	\N	direct-grant-validate-username	89f9614c-27fd-44d6-b07c-c527669d1d5c	b2d9d912-220c-4732-a739-1e3411146d8a	0	10	f	\N	\N
b43d3e73-ef5b-433a-b3f2-cf60bd12ffe2	\N	direct-grant-validate-password	89f9614c-27fd-44d6-b07c-c527669d1d5c	b2d9d912-220c-4732-a739-1e3411146d8a	0	20	f	\N	\N
9e6ab886-6cdd-4b6f-b9e5-416a5678aabd	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	b2d9d912-220c-4732-a739-1e3411146d8a	1	30	t	2f87223c-6c1f-449d-a51e-04971e692a7a	\N
62377c23-1db5-4855-833e-13d42ec24e80	\N	conditional-user-configured	89f9614c-27fd-44d6-b07c-c527669d1d5c	2f87223c-6c1f-449d-a51e-04971e692a7a	0	10	f	\N	\N
240a184a-037b-488c-a69c-e67f7b9bfcaa	\N	direct-grant-validate-otp	89f9614c-27fd-44d6-b07c-c527669d1d5c	2f87223c-6c1f-449d-a51e-04971e692a7a	0	20	f	\N	\N
f5047dd6-0d23-43a7-b0a8-6f03c6d27bc2	\N	registration-page-form	89f9614c-27fd-44d6-b07c-c527669d1d5c	c214dd32-f292-4024-9131-b3facd9d8ff4	0	10	t	db6c425d-8623-4ac8-8472-fdb4ab6a68e2	\N
0e7c9e18-10bf-4994-bea7-dba887620617	\N	registration-user-creation	89f9614c-27fd-44d6-b07c-c527669d1d5c	db6c425d-8623-4ac8-8472-fdb4ab6a68e2	0	20	f	\N	\N
8a4533d8-d1db-4f22-8203-2a167ca42540	\N	registration-profile-action	89f9614c-27fd-44d6-b07c-c527669d1d5c	db6c425d-8623-4ac8-8472-fdb4ab6a68e2	0	40	f	\N	\N
b88da7a8-b0b1-4a31-a089-61b45d122185	\N	registration-password-action	89f9614c-27fd-44d6-b07c-c527669d1d5c	db6c425d-8623-4ac8-8472-fdb4ab6a68e2	0	50	f	\N	\N
3366b37c-eebb-4bd0-8d36-368180288808	\N	registration-recaptcha-action	89f9614c-27fd-44d6-b07c-c527669d1d5c	db6c425d-8623-4ac8-8472-fdb4ab6a68e2	3	60	f	\N	\N
5b49a366-a8b6-4b16-b745-c48924b1a6a6	\N	reset-credentials-choose-user	89f9614c-27fd-44d6-b07c-c527669d1d5c	6bd03fd8-d03a-4cfd-98e3-3518d37dbb85	0	10	f	\N	\N
78010c90-7edd-4d05-a58b-4a2d75597f77	\N	reset-credential-email	89f9614c-27fd-44d6-b07c-c527669d1d5c	6bd03fd8-d03a-4cfd-98e3-3518d37dbb85	0	20	f	\N	\N
49aa5b33-7a25-4780-96fa-8dd9cb29b4be	\N	reset-password	89f9614c-27fd-44d6-b07c-c527669d1d5c	6bd03fd8-d03a-4cfd-98e3-3518d37dbb85	0	30	f	\N	\N
96dd0c0d-9128-459b-bbdc-d3031bfdb702	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	6bd03fd8-d03a-4cfd-98e3-3518d37dbb85	1	40	t	171a99b2-ffc6-4830-9caa-9805e8a51aeb	\N
e5f431dc-0d2b-4ab3-a0b8-d672802379bd	\N	conditional-user-configured	89f9614c-27fd-44d6-b07c-c527669d1d5c	171a99b2-ffc6-4830-9caa-9805e8a51aeb	0	10	f	\N	\N
aa43ff1c-85da-4b57-bfa4-8d98bbdbee35	\N	reset-otp	89f9614c-27fd-44d6-b07c-c527669d1d5c	171a99b2-ffc6-4830-9caa-9805e8a51aeb	0	20	f	\N	\N
7e84d947-09f5-462a-8a31-57d5df206d29	\N	client-secret	89f9614c-27fd-44d6-b07c-c527669d1d5c	c06887ca-f370-40f0-9204-6b4cff53f219	2	10	f	\N	\N
4beee5d9-24c5-41dd-bf44-6c49f2a04c63	\N	client-jwt	89f9614c-27fd-44d6-b07c-c527669d1d5c	c06887ca-f370-40f0-9204-6b4cff53f219	2	20	f	\N	\N
d0e77fac-c397-4364-8deb-1df55401619f	\N	client-secret-jwt	89f9614c-27fd-44d6-b07c-c527669d1d5c	c06887ca-f370-40f0-9204-6b4cff53f219	2	30	f	\N	\N
bbde2282-42e7-4f9c-a85a-4f0f225231d6	\N	client-x509	89f9614c-27fd-44d6-b07c-c527669d1d5c	c06887ca-f370-40f0-9204-6b4cff53f219	2	40	f	\N	\N
52007ec7-e723-4a33-8d05-50b47914b487	\N	idp-review-profile	89f9614c-27fd-44d6-b07c-c527669d1d5c	c442136a-84aa-4ddc-9731-ebdfaeb2e510	0	10	f	\N	3b3f3edb-24c4-457d-8ab4-84fdd665bdb5
883272d5-2ffd-4124-a493-194c7ed000af	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	c442136a-84aa-4ddc-9731-ebdfaeb2e510	0	20	t	2b70b88e-b8e2-4ea5-a47b-4d0feaa8bc43	\N
9c4a9b49-4491-4b2c-a4ff-ae6404e91bec	\N	idp-create-user-if-unique	89f9614c-27fd-44d6-b07c-c527669d1d5c	2b70b88e-b8e2-4ea5-a47b-4d0feaa8bc43	2	10	f	\N	be8ff1c7-dc3f-4710-a313-08f02fe03f37
25f25053-5856-4961-a5f8-9683f4c4a0b6	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	2b70b88e-b8e2-4ea5-a47b-4d0feaa8bc43	2	20	t	3e3bce79-5dac-4db4-8672-9d1e9ce24475	\N
5dc95ebf-7f0b-450f-825e-59cceea29dc8	\N	idp-confirm-link	89f9614c-27fd-44d6-b07c-c527669d1d5c	3e3bce79-5dac-4db4-8672-9d1e9ce24475	0	10	f	\N	\N
135f5d92-b005-4c3d-89c5-7db5f505d9a7	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	3e3bce79-5dac-4db4-8672-9d1e9ce24475	0	20	t	3f1a2053-5a37-4a36-b512-0a36858b8438	\N
d7c2ec47-8728-4bc6-9a08-81dd1d1c76d8	\N	idp-email-verification	89f9614c-27fd-44d6-b07c-c527669d1d5c	3f1a2053-5a37-4a36-b512-0a36858b8438	2	10	f	\N	\N
f52de619-ff74-408c-8500-a1962a3a11f8	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	3f1a2053-5a37-4a36-b512-0a36858b8438	2	20	t	6e19fc11-3cd1-4d53-b8d9-7aebbe03a941	\N
14134105-135d-470e-9ab0-35c6d2588ff4	\N	idp-username-password-form	89f9614c-27fd-44d6-b07c-c527669d1d5c	6e19fc11-3cd1-4d53-b8d9-7aebbe03a941	0	10	f	\N	\N
9bc3995e-988b-4cf3-b656-4437ea40e78f	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	6e19fc11-3cd1-4d53-b8d9-7aebbe03a941	1	20	t	ef8224ce-fd92-47b5-adb1-dc39412f7ab2	\N
6fda7cd0-5d99-4914-a5de-5942b5b6fd23	\N	conditional-user-configured	89f9614c-27fd-44d6-b07c-c527669d1d5c	ef8224ce-fd92-47b5-adb1-dc39412f7ab2	0	10	f	\N	\N
7a81e26a-752f-4a1b-b511-4e134b90ad45	\N	auth-otp-form	89f9614c-27fd-44d6-b07c-c527669d1d5c	ef8224ce-fd92-47b5-adb1-dc39412f7ab2	0	20	f	\N	\N
eeb3ec11-c280-466d-b391-2026b303d82b	\N	http-basic-authenticator	89f9614c-27fd-44d6-b07c-c527669d1d5c	16fa222a-666d-45bc-a7cc-56a6257e5fbd	0	10	f	\N	\N
826dc60c-4cd0-45e6-8309-f3824f2c3b2f	\N	docker-http-basic-authenticator	89f9614c-27fd-44d6-b07c-c527669d1d5c	19c62447-200b-46ea-ac1f-08f03adb4fdb	0	10	f	\N	\N
0703bc0a-3b6e-41c9-8110-5e2ce269acc3	\N	no-cookie-redirect	89f9614c-27fd-44d6-b07c-c527669d1d5c	323e6730-78fd-4fda-894a-62e3a4904193	0	10	f	\N	\N
d2e2b90e-deb3-46e7-bbc0-9d8a817b9326	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	323e6730-78fd-4fda-894a-62e3a4904193	0	20	t	3c1eff9f-477f-46d8-9877-29ed8585db48	\N
1bc67993-2960-4278-99be-280d29fb2abe	\N	basic-auth	89f9614c-27fd-44d6-b07c-c527669d1d5c	3c1eff9f-477f-46d8-9877-29ed8585db48	0	10	f	\N	\N
5c81efb2-12be-4212-a57c-9d13c9f5a2be	\N	basic-auth-otp	89f9614c-27fd-44d6-b07c-c527669d1d5c	3c1eff9f-477f-46d8-9877-29ed8585db48	3	20	f	\N	\N
ac3052f0-34ab-4610-99af-8f23c4c7554e	\N	auth-spnego	89f9614c-27fd-44d6-b07c-c527669d1d5c	3c1eff9f-477f-46d8-9877-29ed8585db48	3	30	f	\N	\N
\.


--
-- Data for Name: authentication_flow; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.authentication_flow (id, alias, description, realm_id, provider_id, top_level, built_in) FROM stdin;
34851a15-ddd8-47e2-8b49-6a04d4d64d89	browser	browser based authentication	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	t	t
d981a4f6-d2ea-495a-9d01-dbbf670a0774	forms	Username, password, otp and other auth forms.	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	f	t
851d7bea-1637-47d4-8feb-c826417b06a2	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	f	t
3f198a37-b5ed-4b11-8847-d1937a4fc56e	direct grant	OpenID Connect Resource Owner Grant	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	t	t
69b2efe2-ee2a-4e6c-b8c9-2b6d40d9af96	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	f	t
81cefadf-789e-4571-8c51-a3fba9e5857c	registration	registration flow	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	t	t
4ee1d709-b916-4841-a9fe-a861946040fa	registration form	registration form	fc3e583c-0722-4ee1-aeef-70af67cf78ba	form-flow	f	t
b51ea154-95e5-4ec4-9547-a74a9f5d36f9	reset credentials	Reset credentials for a user if they forgot their password or something	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	t	t
198afb3d-8c4f-488c-a264-a88c17fd0fe2	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	f	t
eb100bd9-a3b0-411a-b806-897af36a7bbc	clients	Base authentication for clients	fc3e583c-0722-4ee1-aeef-70af67cf78ba	client-flow	t	t
a9594579-18d4-4176-8ec7-03c2c89c0cd3	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	t	t
a75a13d2-e1ab-4ad1-b27b-1c24cdb5e38b	User creation or linking	Flow for the existing/non-existing user alternatives	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	f	t
f43a18a5-f738-4ae6-8cb4-4550c3bf089b	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	f	t
45a275d0-ed07-4fc0-994d-39ad9ee2d102	Account verification options	Method with which to verity the existing account	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	f	t
061baee4-a666-4ba4-8362-456613c679c4	Verify Existing Account by Re-authentication	Reauthentication of existing account	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	f	t
0677a714-d7b9-4c2a-b7f5-8ced77e6702a	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	f	t
c848bf58-8bdb-43f3-954b-a2b3d0ef2dfb	saml ecp	SAML ECP Profile Authentication Flow	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	t	t
1206965f-dcbd-4871-a2a3-b26b59971301	docker auth	Used by Docker clients to authenticate against the IDP	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	t	t
f5fb3bf1-c145-4c15-86bb-9442c62f39bf	http challenge	An authentication flow based on challenge-response HTTP Authentication Schemes	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	t	t
299f2afd-6a86-4d56-ac98-2318c761bf77	Authentication Options	Authentication options.	fc3e583c-0722-4ee1-aeef-70af67cf78ba	basic-flow	f	t
9d711a7a-1fc5-4553-88ad-a11a4c1115b3	browser	browser based authentication	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	t	t
d9dde025-1e16-46ff-a623-e0d5e8d74f3b	forms	Username, password, otp and other auth forms.	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	f	t
5ae0e3e1-28ab-4c59-b851-1a877b685cd6	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	f	t
b2d9d912-220c-4732-a739-1e3411146d8a	direct grant	OpenID Connect Resource Owner Grant	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	t	t
2f87223c-6c1f-449d-a51e-04971e692a7a	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	f	t
c214dd32-f292-4024-9131-b3facd9d8ff4	registration	registration flow	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	t	t
db6c425d-8623-4ac8-8472-fdb4ab6a68e2	registration form	registration form	89f9614c-27fd-44d6-b07c-c527669d1d5c	form-flow	f	t
6bd03fd8-d03a-4cfd-98e3-3518d37dbb85	reset credentials	Reset credentials for a user if they forgot their password or something	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	t	t
171a99b2-ffc6-4830-9caa-9805e8a51aeb	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	f	t
c06887ca-f370-40f0-9204-6b4cff53f219	clients	Base authentication for clients	89f9614c-27fd-44d6-b07c-c527669d1d5c	client-flow	t	t
c442136a-84aa-4ddc-9731-ebdfaeb2e510	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	t	t
2b70b88e-b8e2-4ea5-a47b-4d0feaa8bc43	User creation or linking	Flow for the existing/non-existing user alternatives	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	f	t
3e3bce79-5dac-4db4-8672-9d1e9ce24475	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	f	t
3f1a2053-5a37-4a36-b512-0a36858b8438	Account verification options	Method with which to verity the existing account	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	f	t
6e19fc11-3cd1-4d53-b8d9-7aebbe03a941	Verify Existing Account by Re-authentication	Reauthentication of existing account	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	f	t
ef8224ce-fd92-47b5-adb1-dc39412f7ab2	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	f	t
16fa222a-666d-45bc-a7cc-56a6257e5fbd	saml ecp	SAML ECP Profile Authentication Flow	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	t	t
19c62447-200b-46ea-ac1f-08f03adb4fdb	docker auth	Used by Docker clients to authenticate against the IDP	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	t	t
323e6730-78fd-4fda-894a-62e3a4904193	http challenge	An authentication flow based on challenge-response HTTP Authentication Schemes	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	t	t
3c1eff9f-477f-46d8-9877-29ed8585db48	Authentication Options	Authentication options.	89f9614c-27fd-44d6-b07c-c527669d1d5c	basic-flow	f	t
\.


--
-- Data for Name: authenticator_config; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.authenticator_config (id, alias, realm_id) FROM stdin;
0ff9d931-cb83-4dca-9087-629c21a04935	review profile config	fc3e583c-0722-4ee1-aeef-70af67cf78ba
4ea3bf9e-7a76-442a-8681-5a89af09321d	create unique user config	fc3e583c-0722-4ee1-aeef-70af67cf78ba
3b3f3edb-24c4-457d-8ab4-84fdd665bdb5	review profile config	89f9614c-27fd-44d6-b07c-c527669d1d5c
be8ff1c7-dc3f-4710-a313-08f02fe03f37	create unique user config	89f9614c-27fd-44d6-b07c-c527669d1d5c
\.


--
-- Data for Name: authenticator_config_entry; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.authenticator_config_entry (authenticator_id, value, name) FROM stdin;
0ff9d931-cb83-4dca-9087-629c21a04935	missing	update.profile.on.first.login
4ea3bf9e-7a76-442a-8681-5a89af09321d	false	require.password.update.after.registration
3b3f3edb-24c4-457d-8ab4-84fdd665bdb5	missing	update.profile.on.first.login
be8ff1c7-dc3f-4710-a313-08f02fe03f37	false	require.password.update.after.registration
\.


--
-- Data for Name: broker_link; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.broker_link (identity_provider, storage_provider_id, realm_id, broker_user_id, broker_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client (id, enabled, full_scope_allowed, client_id, not_before, public_client, secret, base_url, bearer_only, management_url, surrogate_auth_required, realm_id, protocol, node_rereg_timeout, frontchannel_logout, consent_required, name, service_accounts_enabled, client_authenticator_type, root_url, description, registration_token, standard_flow_enabled, implicit_flow_enabled, direct_access_grants_enabled, always_display_in_console) FROM stdin;
2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	f	master-realm	0	f	\N	\N	t	\N	f	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	0	f	f	master Realm	f	client-secret	\N	\N	\N	t	f	f	f
7626857f-0274-4b0c-90bc-996f940fc6d9	t	f	account	0	t	\N	/realms/master/account/	f	\N	f	fc3e583c-0722-4ee1-aeef-70af67cf78ba	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
8f880efc-b681-4dd1-bc3f-a71b772ef16b	t	f	account-console	0	t	\N	/realms/master/account/	f	\N	f	fc3e583c-0722-4ee1-aeef-70af67cf78ba	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
e212016a-a377-4661-8acc-6ec56608a8dd	t	f	broker	0	f	\N	\N	t	\N	f	fc3e583c-0722-4ee1-aeef-70af67cf78ba	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
194b8b1c-ff39-4947-bac4-a35411a838fd	t	f	security-admin-console	0	t	\N	/admin/master/console/	f	\N	f	fc3e583c-0722-4ee1-aeef-70af67cf78ba	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
b7690954-3791-47de-bc65-7993833a3025	t	f	admin-cli	0	t	\N	\N	f	\N	f	fc3e583c-0722-4ee1-aeef-70af67cf78ba	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	f	realm-pantanal-dev-realm	0	f	\N	\N	t	\N	f	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	0	f	f	realm-pantanal-dev Realm	f	client-secret	\N	\N	\N	t	f	f	f
38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	f	realm-management	0	f	\N	\N	t	\N	f	89f9614c-27fd-44d6-b07c-c527669d1d5c	openid-connect	0	f	f	${client_realm-management}	f	client-secret	\N	\N	\N	t	f	f	f
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	t	f	account	0	t	\N	/realms/realm-pantanal-dev/account/	f	\N	f	89f9614c-27fd-44d6-b07c-c527669d1d5c	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
c559f39f-2212-4360-83ed-4829e52c3e24	t	f	account-console	0	t	\N	/realms/realm-pantanal-dev/account/	f	\N	f	89f9614c-27fd-44d6-b07c-c527669d1d5c	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
78c7cc85-99eb-4a64-903b-f0730fdf1383	t	f	broker	0	f	\N	\N	t	\N	f	89f9614c-27fd-44d6-b07c-c527669d1d5c	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
1b41c01a-a904-41a8-9c76-88a152c41ac5	t	f	security-admin-console	0	t	\N	/admin/realm-pantanal-dev/console/	f	\N	f	89f9614c-27fd-44d6-b07c-c527669d1d5c	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
d897413c-7b7e-4100-af23-168435cf5e6e	t	f	admin-cli	0	t	\N	\N	f	\N	f	89f9614c-27fd-44d6-b07c-c527669d1d5c	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	t	t	client-id-backend-1	0	f	uXfLOkhKTekWg674dKs41GmU0a1vhHzG	http://localhost:3001/	f	http://localhost:3001/	f	89f9614c-27fd-44d6-b07c-c527669d1d5c	openid-connect	-1	t	f	client-id-backend-1	t	client-secret	http://localhost:3001/		\N	t	f	t	f
\.


--
-- Data for Name: client_attributes; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_attributes (client_id, name, value) FROM stdin;
7626857f-0274-4b0c-90bc-996f940fc6d9	post.logout.redirect.uris	+
8f880efc-b681-4dd1-bc3f-a71b772ef16b	post.logout.redirect.uris	+
8f880efc-b681-4dd1-bc3f-a71b772ef16b	pkce.code.challenge.method	S256
194b8b1c-ff39-4947-bac4-a35411a838fd	post.logout.redirect.uris	+
194b8b1c-ff39-4947-bac4-a35411a838fd	pkce.code.challenge.method	S256
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	post.logout.redirect.uris	+
c559f39f-2212-4360-83ed-4829e52c3e24	post.logout.redirect.uris	+
c559f39f-2212-4360-83ed-4829e52c3e24	pkce.code.challenge.method	S256
1b41c01a-a904-41a8-9c76-88a152c41ac5	post.logout.redirect.uris	+
1b41c01a-a904-41a8-9c76-88a152c41ac5	pkce.code.challenge.method	S256
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	client.secret.creation.time	1695175050
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	oauth2.device.authorization.grant.enabled	false
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	oidc.ciba.grant.enabled	false
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	backchannel.logout.session.required	true
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	backchannel.logout.revoke.offline.tokens	false
\.


--
-- Data for Name: client_auth_flow_bindings; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_auth_flow_bindings (client_id, flow_id, binding_name) FROM stdin;
\.


--
-- Data for Name: client_initial_access; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_initial_access (id, realm_id, "timestamp", expiration, count, remaining_count) FROM stdin;
\.


--
-- Data for Name: client_node_registrations; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_node_registrations (client_id, value, name) FROM stdin;
\.


--
-- Data for Name: client_scope; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_scope (id, name, realm_id, description, protocol) FROM stdin;
9675f3c3-801f-4102-8165-5a2b91c820e8	offline_access	fc3e583c-0722-4ee1-aeef-70af67cf78ba	OpenID Connect built-in scope: offline_access	openid-connect
127baadd-fd95-4586-8c9a-774c0ad2e6a7	role_list	fc3e583c-0722-4ee1-aeef-70af67cf78ba	SAML role list	saml
3953f190-5b31-4665-af77-301a3b04cb29	profile	fc3e583c-0722-4ee1-aeef-70af67cf78ba	OpenID Connect built-in scope: profile	openid-connect
7199870d-2829-4c8b-8e69-f3b3719078c8	email	fc3e583c-0722-4ee1-aeef-70af67cf78ba	OpenID Connect built-in scope: email	openid-connect
2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	address	fc3e583c-0722-4ee1-aeef-70af67cf78ba	OpenID Connect built-in scope: address	openid-connect
8571a084-e752-4bc0-826e-5c3101b2806d	phone	fc3e583c-0722-4ee1-aeef-70af67cf78ba	OpenID Connect built-in scope: phone	openid-connect
410cc8bb-cebd-4584-b751-c7338c65ddfd	roles	fc3e583c-0722-4ee1-aeef-70af67cf78ba	OpenID Connect scope for add user roles to the access token	openid-connect
4bed6796-2ccc-484c-aedd-3c6d10fb1be9	web-origins	fc3e583c-0722-4ee1-aeef-70af67cf78ba	OpenID Connect scope for add allowed web origins to the access token	openid-connect
e79f8057-e166-476f-aef4-3acb08abc6c2	microprofile-jwt	fc3e583c-0722-4ee1-aeef-70af67cf78ba	Microprofile - JWT built-in scope	openid-connect
052aba50-63e4-4959-85f3-5bcbd50aab37	acr	fc3e583c-0722-4ee1-aeef-70af67cf78ba	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
37e9482e-66ca-4472-b441-a8241d28f09e	offline_access	89f9614c-27fd-44d6-b07c-c527669d1d5c	OpenID Connect built-in scope: offline_access	openid-connect
98920d25-d442-4201-8c78-76726c58ca8e	role_list	89f9614c-27fd-44d6-b07c-c527669d1d5c	SAML role list	saml
35bd4d78-1490-42bc-8588-4b6b48231f94	profile	89f9614c-27fd-44d6-b07c-c527669d1d5c	OpenID Connect built-in scope: profile	openid-connect
e8cbe350-c045-4f95-a4af-17fd5672cbc3	email	89f9614c-27fd-44d6-b07c-c527669d1d5c	OpenID Connect built-in scope: email	openid-connect
fee9a005-61ba-4f89-8be1-f66d3aa9d73b	address	89f9614c-27fd-44d6-b07c-c527669d1d5c	OpenID Connect built-in scope: address	openid-connect
0ab80d9d-976f-45fb-9531-5bbb53abdc47	phone	89f9614c-27fd-44d6-b07c-c527669d1d5c	OpenID Connect built-in scope: phone	openid-connect
1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	roles	89f9614c-27fd-44d6-b07c-c527669d1d5c	OpenID Connect scope for add user roles to the access token	openid-connect
0595dccb-d437-44a9-92b7-2458928021ef	web-origins	89f9614c-27fd-44d6-b07c-c527669d1d5c	OpenID Connect scope for add allowed web origins to the access token	openid-connect
3c40af86-1182-49d5-ba42-50eed305b394	microprofile-jwt	89f9614c-27fd-44d6-b07c-c527669d1d5c	Microprofile - JWT built-in scope	openid-connect
3db49690-a543-488c-b811-1eb95b78a43d	acr	89f9614c-27fd-44d6-b07c-c527669d1d5c	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
\.


--
-- Data for Name: client_scope_attributes; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_scope_attributes (scope_id, value, name) FROM stdin;
9675f3c3-801f-4102-8165-5a2b91c820e8	true	display.on.consent.screen
9675f3c3-801f-4102-8165-5a2b91c820e8	${offlineAccessScopeConsentText}	consent.screen.text
127baadd-fd95-4586-8c9a-774c0ad2e6a7	true	display.on.consent.screen
127baadd-fd95-4586-8c9a-774c0ad2e6a7	${samlRoleListScopeConsentText}	consent.screen.text
3953f190-5b31-4665-af77-301a3b04cb29	true	display.on.consent.screen
3953f190-5b31-4665-af77-301a3b04cb29	${profileScopeConsentText}	consent.screen.text
3953f190-5b31-4665-af77-301a3b04cb29	true	include.in.token.scope
7199870d-2829-4c8b-8e69-f3b3719078c8	true	display.on.consent.screen
7199870d-2829-4c8b-8e69-f3b3719078c8	${emailScopeConsentText}	consent.screen.text
7199870d-2829-4c8b-8e69-f3b3719078c8	true	include.in.token.scope
2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	true	display.on.consent.screen
2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	${addressScopeConsentText}	consent.screen.text
2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	true	include.in.token.scope
8571a084-e752-4bc0-826e-5c3101b2806d	true	display.on.consent.screen
8571a084-e752-4bc0-826e-5c3101b2806d	${phoneScopeConsentText}	consent.screen.text
8571a084-e752-4bc0-826e-5c3101b2806d	true	include.in.token.scope
410cc8bb-cebd-4584-b751-c7338c65ddfd	true	display.on.consent.screen
410cc8bb-cebd-4584-b751-c7338c65ddfd	${rolesScopeConsentText}	consent.screen.text
410cc8bb-cebd-4584-b751-c7338c65ddfd	false	include.in.token.scope
4bed6796-2ccc-484c-aedd-3c6d10fb1be9	false	display.on.consent.screen
4bed6796-2ccc-484c-aedd-3c6d10fb1be9		consent.screen.text
4bed6796-2ccc-484c-aedd-3c6d10fb1be9	false	include.in.token.scope
e79f8057-e166-476f-aef4-3acb08abc6c2	false	display.on.consent.screen
e79f8057-e166-476f-aef4-3acb08abc6c2	true	include.in.token.scope
052aba50-63e4-4959-85f3-5bcbd50aab37	false	display.on.consent.screen
052aba50-63e4-4959-85f3-5bcbd50aab37	false	include.in.token.scope
37e9482e-66ca-4472-b441-a8241d28f09e	true	display.on.consent.screen
37e9482e-66ca-4472-b441-a8241d28f09e	${offlineAccessScopeConsentText}	consent.screen.text
98920d25-d442-4201-8c78-76726c58ca8e	true	display.on.consent.screen
98920d25-d442-4201-8c78-76726c58ca8e	${samlRoleListScopeConsentText}	consent.screen.text
35bd4d78-1490-42bc-8588-4b6b48231f94	true	display.on.consent.screen
35bd4d78-1490-42bc-8588-4b6b48231f94	${profileScopeConsentText}	consent.screen.text
35bd4d78-1490-42bc-8588-4b6b48231f94	true	include.in.token.scope
e8cbe350-c045-4f95-a4af-17fd5672cbc3	true	display.on.consent.screen
e8cbe350-c045-4f95-a4af-17fd5672cbc3	${emailScopeConsentText}	consent.screen.text
e8cbe350-c045-4f95-a4af-17fd5672cbc3	true	include.in.token.scope
fee9a005-61ba-4f89-8be1-f66d3aa9d73b	true	display.on.consent.screen
fee9a005-61ba-4f89-8be1-f66d3aa9d73b	${addressScopeConsentText}	consent.screen.text
fee9a005-61ba-4f89-8be1-f66d3aa9d73b	true	include.in.token.scope
0ab80d9d-976f-45fb-9531-5bbb53abdc47	true	display.on.consent.screen
0ab80d9d-976f-45fb-9531-5bbb53abdc47	${phoneScopeConsentText}	consent.screen.text
0ab80d9d-976f-45fb-9531-5bbb53abdc47	true	include.in.token.scope
1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	true	display.on.consent.screen
1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	${rolesScopeConsentText}	consent.screen.text
1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	false	include.in.token.scope
0595dccb-d437-44a9-92b7-2458928021ef	false	display.on.consent.screen
0595dccb-d437-44a9-92b7-2458928021ef		consent.screen.text
0595dccb-d437-44a9-92b7-2458928021ef	false	include.in.token.scope
3c40af86-1182-49d5-ba42-50eed305b394	false	display.on.consent.screen
3c40af86-1182-49d5-ba42-50eed305b394	true	include.in.token.scope
3db49690-a543-488c-b811-1eb95b78a43d	false	display.on.consent.screen
3db49690-a543-488c-b811-1eb95b78a43d	false	include.in.token.scope
\.


--
-- Data for Name: client_scope_client; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_scope_client (client_id, scope_id, default_scope) FROM stdin;
7626857f-0274-4b0c-90bc-996f940fc6d9	052aba50-63e4-4959-85f3-5bcbd50aab37	t
7626857f-0274-4b0c-90bc-996f940fc6d9	7199870d-2829-4c8b-8e69-f3b3719078c8	t
7626857f-0274-4b0c-90bc-996f940fc6d9	3953f190-5b31-4665-af77-301a3b04cb29	t
7626857f-0274-4b0c-90bc-996f940fc6d9	4bed6796-2ccc-484c-aedd-3c6d10fb1be9	t
7626857f-0274-4b0c-90bc-996f940fc6d9	410cc8bb-cebd-4584-b751-c7338c65ddfd	t
7626857f-0274-4b0c-90bc-996f940fc6d9	e79f8057-e166-476f-aef4-3acb08abc6c2	f
7626857f-0274-4b0c-90bc-996f940fc6d9	8571a084-e752-4bc0-826e-5c3101b2806d	f
7626857f-0274-4b0c-90bc-996f940fc6d9	2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	f
7626857f-0274-4b0c-90bc-996f940fc6d9	9675f3c3-801f-4102-8165-5a2b91c820e8	f
8f880efc-b681-4dd1-bc3f-a71b772ef16b	052aba50-63e4-4959-85f3-5bcbd50aab37	t
8f880efc-b681-4dd1-bc3f-a71b772ef16b	7199870d-2829-4c8b-8e69-f3b3719078c8	t
8f880efc-b681-4dd1-bc3f-a71b772ef16b	3953f190-5b31-4665-af77-301a3b04cb29	t
8f880efc-b681-4dd1-bc3f-a71b772ef16b	4bed6796-2ccc-484c-aedd-3c6d10fb1be9	t
8f880efc-b681-4dd1-bc3f-a71b772ef16b	410cc8bb-cebd-4584-b751-c7338c65ddfd	t
8f880efc-b681-4dd1-bc3f-a71b772ef16b	e79f8057-e166-476f-aef4-3acb08abc6c2	f
8f880efc-b681-4dd1-bc3f-a71b772ef16b	8571a084-e752-4bc0-826e-5c3101b2806d	f
8f880efc-b681-4dd1-bc3f-a71b772ef16b	2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	f
8f880efc-b681-4dd1-bc3f-a71b772ef16b	9675f3c3-801f-4102-8165-5a2b91c820e8	f
b7690954-3791-47de-bc65-7993833a3025	052aba50-63e4-4959-85f3-5bcbd50aab37	t
b7690954-3791-47de-bc65-7993833a3025	7199870d-2829-4c8b-8e69-f3b3719078c8	t
b7690954-3791-47de-bc65-7993833a3025	3953f190-5b31-4665-af77-301a3b04cb29	t
b7690954-3791-47de-bc65-7993833a3025	4bed6796-2ccc-484c-aedd-3c6d10fb1be9	t
b7690954-3791-47de-bc65-7993833a3025	410cc8bb-cebd-4584-b751-c7338c65ddfd	t
b7690954-3791-47de-bc65-7993833a3025	e79f8057-e166-476f-aef4-3acb08abc6c2	f
b7690954-3791-47de-bc65-7993833a3025	8571a084-e752-4bc0-826e-5c3101b2806d	f
b7690954-3791-47de-bc65-7993833a3025	2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	f
b7690954-3791-47de-bc65-7993833a3025	9675f3c3-801f-4102-8165-5a2b91c820e8	f
e212016a-a377-4661-8acc-6ec56608a8dd	052aba50-63e4-4959-85f3-5bcbd50aab37	t
e212016a-a377-4661-8acc-6ec56608a8dd	7199870d-2829-4c8b-8e69-f3b3719078c8	t
e212016a-a377-4661-8acc-6ec56608a8dd	3953f190-5b31-4665-af77-301a3b04cb29	t
e212016a-a377-4661-8acc-6ec56608a8dd	4bed6796-2ccc-484c-aedd-3c6d10fb1be9	t
e212016a-a377-4661-8acc-6ec56608a8dd	410cc8bb-cebd-4584-b751-c7338c65ddfd	t
e212016a-a377-4661-8acc-6ec56608a8dd	e79f8057-e166-476f-aef4-3acb08abc6c2	f
e212016a-a377-4661-8acc-6ec56608a8dd	8571a084-e752-4bc0-826e-5c3101b2806d	f
e212016a-a377-4661-8acc-6ec56608a8dd	2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	f
e212016a-a377-4661-8acc-6ec56608a8dd	9675f3c3-801f-4102-8165-5a2b91c820e8	f
2f4a167a-e929-4f60-bb94-eb7a45fdfc31	052aba50-63e4-4959-85f3-5bcbd50aab37	t
2f4a167a-e929-4f60-bb94-eb7a45fdfc31	7199870d-2829-4c8b-8e69-f3b3719078c8	t
2f4a167a-e929-4f60-bb94-eb7a45fdfc31	3953f190-5b31-4665-af77-301a3b04cb29	t
2f4a167a-e929-4f60-bb94-eb7a45fdfc31	4bed6796-2ccc-484c-aedd-3c6d10fb1be9	t
2f4a167a-e929-4f60-bb94-eb7a45fdfc31	410cc8bb-cebd-4584-b751-c7338c65ddfd	t
2f4a167a-e929-4f60-bb94-eb7a45fdfc31	e79f8057-e166-476f-aef4-3acb08abc6c2	f
2f4a167a-e929-4f60-bb94-eb7a45fdfc31	8571a084-e752-4bc0-826e-5c3101b2806d	f
2f4a167a-e929-4f60-bb94-eb7a45fdfc31	2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	f
2f4a167a-e929-4f60-bb94-eb7a45fdfc31	9675f3c3-801f-4102-8165-5a2b91c820e8	f
194b8b1c-ff39-4947-bac4-a35411a838fd	052aba50-63e4-4959-85f3-5bcbd50aab37	t
194b8b1c-ff39-4947-bac4-a35411a838fd	7199870d-2829-4c8b-8e69-f3b3719078c8	t
194b8b1c-ff39-4947-bac4-a35411a838fd	3953f190-5b31-4665-af77-301a3b04cb29	t
194b8b1c-ff39-4947-bac4-a35411a838fd	4bed6796-2ccc-484c-aedd-3c6d10fb1be9	t
194b8b1c-ff39-4947-bac4-a35411a838fd	410cc8bb-cebd-4584-b751-c7338c65ddfd	t
194b8b1c-ff39-4947-bac4-a35411a838fd	e79f8057-e166-476f-aef4-3acb08abc6c2	f
194b8b1c-ff39-4947-bac4-a35411a838fd	8571a084-e752-4bc0-826e-5c3101b2806d	f
194b8b1c-ff39-4947-bac4-a35411a838fd	2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	f
194b8b1c-ff39-4947-bac4-a35411a838fd	9675f3c3-801f-4102-8165-5a2b91c820e8	f
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	3db49690-a543-488c-b811-1eb95b78a43d	t
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	e8cbe350-c045-4f95-a4af-17fd5672cbc3	t
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	t
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	35bd4d78-1490-42bc-8588-4b6b48231f94	t
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	0595dccb-d437-44a9-92b7-2458928021ef	t
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	37e9482e-66ca-4472-b441-a8241d28f09e	f
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	0ab80d9d-976f-45fb-9531-5bbb53abdc47	f
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	fee9a005-61ba-4f89-8be1-f66d3aa9d73b	f
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	3c40af86-1182-49d5-ba42-50eed305b394	f
c559f39f-2212-4360-83ed-4829e52c3e24	3db49690-a543-488c-b811-1eb95b78a43d	t
c559f39f-2212-4360-83ed-4829e52c3e24	e8cbe350-c045-4f95-a4af-17fd5672cbc3	t
c559f39f-2212-4360-83ed-4829e52c3e24	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	t
c559f39f-2212-4360-83ed-4829e52c3e24	35bd4d78-1490-42bc-8588-4b6b48231f94	t
c559f39f-2212-4360-83ed-4829e52c3e24	0595dccb-d437-44a9-92b7-2458928021ef	t
c559f39f-2212-4360-83ed-4829e52c3e24	37e9482e-66ca-4472-b441-a8241d28f09e	f
c559f39f-2212-4360-83ed-4829e52c3e24	0ab80d9d-976f-45fb-9531-5bbb53abdc47	f
c559f39f-2212-4360-83ed-4829e52c3e24	fee9a005-61ba-4f89-8be1-f66d3aa9d73b	f
c559f39f-2212-4360-83ed-4829e52c3e24	3c40af86-1182-49d5-ba42-50eed305b394	f
d897413c-7b7e-4100-af23-168435cf5e6e	3db49690-a543-488c-b811-1eb95b78a43d	t
d897413c-7b7e-4100-af23-168435cf5e6e	e8cbe350-c045-4f95-a4af-17fd5672cbc3	t
d897413c-7b7e-4100-af23-168435cf5e6e	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	t
d897413c-7b7e-4100-af23-168435cf5e6e	35bd4d78-1490-42bc-8588-4b6b48231f94	t
d897413c-7b7e-4100-af23-168435cf5e6e	0595dccb-d437-44a9-92b7-2458928021ef	t
d897413c-7b7e-4100-af23-168435cf5e6e	37e9482e-66ca-4472-b441-a8241d28f09e	f
d897413c-7b7e-4100-af23-168435cf5e6e	0ab80d9d-976f-45fb-9531-5bbb53abdc47	f
d897413c-7b7e-4100-af23-168435cf5e6e	fee9a005-61ba-4f89-8be1-f66d3aa9d73b	f
d897413c-7b7e-4100-af23-168435cf5e6e	3c40af86-1182-49d5-ba42-50eed305b394	f
78c7cc85-99eb-4a64-903b-f0730fdf1383	3db49690-a543-488c-b811-1eb95b78a43d	t
78c7cc85-99eb-4a64-903b-f0730fdf1383	e8cbe350-c045-4f95-a4af-17fd5672cbc3	t
78c7cc85-99eb-4a64-903b-f0730fdf1383	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	t
78c7cc85-99eb-4a64-903b-f0730fdf1383	35bd4d78-1490-42bc-8588-4b6b48231f94	t
78c7cc85-99eb-4a64-903b-f0730fdf1383	0595dccb-d437-44a9-92b7-2458928021ef	t
78c7cc85-99eb-4a64-903b-f0730fdf1383	37e9482e-66ca-4472-b441-a8241d28f09e	f
78c7cc85-99eb-4a64-903b-f0730fdf1383	0ab80d9d-976f-45fb-9531-5bbb53abdc47	f
78c7cc85-99eb-4a64-903b-f0730fdf1383	fee9a005-61ba-4f89-8be1-f66d3aa9d73b	f
78c7cc85-99eb-4a64-903b-f0730fdf1383	3c40af86-1182-49d5-ba42-50eed305b394	f
38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	3db49690-a543-488c-b811-1eb95b78a43d	t
38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	e8cbe350-c045-4f95-a4af-17fd5672cbc3	t
38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	t
38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	35bd4d78-1490-42bc-8588-4b6b48231f94	t
38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	0595dccb-d437-44a9-92b7-2458928021ef	t
38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	37e9482e-66ca-4472-b441-a8241d28f09e	f
38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	0ab80d9d-976f-45fb-9531-5bbb53abdc47	f
38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	fee9a005-61ba-4f89-8be1-f66d3aa9d73b	f
38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	3c40af86-1182-49d5-ba42-50eed305b394	f
1b41c01a-a904-41a8-9c76-88a152c41ac5	3db49690-a543-488c-b811-1eb95b78a43d	t
1b41c01a-a904-41a8-9c76-88a152c41ac5	e8cbe350-c045-4f95-a4af-17fd5672cbc3	t
1b41c01a-a904-41a8-9c76-88a152c41ac5	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	t
1b41c01a-a904-41a8-9c76-88a152c41ac5	35bd4d78-1490-42bc-8588-4b6b48231f94	t
1b41c01a-a904-41a8-9c76-88a152c41ac5	0595dccb-d437-44a9-92b7-2458928021ef	t
1b41c01a-a904-41a8-9c76-88a152c41ac5	37e9482e-66ca-4472-b441-a8241d28f09e	f
1b41c01a-a904-41a8-9c76-88a152c41ac5	0ab80d9d-976f-45fb-9531-5bbb53abdc47	f
1b41c01a-a904-41a8-9c76-88a152c41ac5	fee9a005-61ba-4f89-8be1-f66d3aa9d73b	f
1b41c01a-a904-41a8-9c76-88a152c41ac5	3c40af86-1182-49d5-ba42-50eed305b394	f
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	3db49690-a543-488c-b811-1eb95b78a43d	t
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	e8cbe350-c045-4f95-a4af-17fd5672cbc3	t
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	t
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	35bd4d78-1490-42bc-8588-4b6b48231f94	t
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	0595dccb-d437-44a9-92b7-2458928021ef	t
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	37e9482e-66ca-4472-b441-a8241d28f09e	f
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	0ab80d9d-976f-45fb-9531-5bbb53abdc47	f
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	fee9a005-61ba-4f89-8be1-f66d3aa9d73b	f
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	3c40af86-1182-49d5-ba42-50eed305b394	f
\.


--
-- Data for Name: client_scope_role_mapping; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_scope_role_mapping (scope_id, role_id) FROM stdin;
9675f3c3-801f-4102-8165-5a2b91c820e8	5690ce3d-1ea7-4f4d-8cc1-2777667c0aa3
37e9482e-66ca-4472-b441-a8241d28f09e	8eb3be08-fd48-4e0b-8a58-7488f1a8f69c
\.


--
-- Data for Name: client_session; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_session (id, client_id, redirect_uri, state, "timestamp", session_id, auth_method, realm_id, auth_user_id, current_action) FROM stdin;
\.


--
-- Data for Name: client_session_auth_status; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_session_auth_status (authenticator, status, client_session) FROM stdin;
\.


--
-- Data for Name: client_session_note; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_session_note (name, value, client_session) FROM stdin;
\.


--
-- Data for Name: client_session_prot_mapper; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_session_prot_mapper (protocol_mapper_id, client_session) FROM stdin;
\.


--
-- Data for Name: client_session_role; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_session_role (role_id, client_session) FROM stdin;
\.


--
-- Data for Name: client_user_session_note; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.client_user_session_note (name, value, client_session) FROM stdin;
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.component (id, name, parent_id, provider_id, provider_type, realm_id, sub_type) FROM stdin;
07bc91d3-c535-4e06-b9b4-f50950754ab4	Trusted Hosts	fc3e583c-0722-4ee1-aeef-70af67cf78ba	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	fc3e583c-0722-4ee1-aeef-70af67cf78ba	anonymous
a6c3828b-cb3e-4d90-bc4b-e06e5a84c82c	Consent Required	fc3e583c-0722-4ee1-aeef-70af67cf78ba	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	fc3e583c-0722-4ee1-aeef-70af67cf78ba	anonymous
c0dd759c-9c89-4175-90dc-51685c1a4dc2	Full Scope Disabled	fc3e583c-0722-4ee1-aeef-70af67cf78ba	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	fc3e583c-0722-4ee1-aeef-70af67cf78ba	anonymous
56bdeb96-6673-435b-9c66-721704f418a5	Max Clients Limit	fc3e583c-0722-4ee1-aeef-70af67cf78ba	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	fc3e583c-0722-4ee1-aeef-70af67cf78ba	anonymous
ab9a80b4-cd88-4a20-b6db-7694b91c204d	Allowed Protocol Mapper Types	fc3e583c-0722-4ee1-aeef-70af67cf78ba	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	fc3e583c-0722-4ee1-aeef-70af67cf78ba	anonymous
3e6ed46e-4477-4492-9518-8e1f4f100d9f	Allowed Client Scopes	fc3e583c-0722-4ee1-aeef-70af67cf78ba	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	fc3e583c-0722-4ee1-aeef-70af67cf78ba	anonymous
64a9271e-d7e3-41cb-b5a4-6d3085932ad2	Allowed Protocol Mapper Types	fc3e583c-0722-4ee1-aeef-70af67cf78ba	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	fc3e583c-0722-4ee1-aeef-70af67cf78ba	authenticated
6ceb63a8-48a5-42b9-9d61-0a1b04b9e69e	Allowed Client Scopes	fc3e583c-0722-4ee1-aeef-70af67cf78ba	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	fc3e583c-0722-4ee1-aeef-70af67cf78ba	authenticated
de1653e3-d5ca-47a5-bb69-7f00e9e1160a	rsa-generated	fc3e583c-0722-4ee1-aeef-70af67cf78ba	rsa-generated	org.keycloak.keys.KeyProvider	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N
036e4791-e042-4516-a99a-c81761ed9a00	rsa-enc-generated	fc3e583c-0722-4ee1-aeef-70af67cf78ba	rsa-enc-generated	org.keycloak.keys.KeyProvider	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N
ffbc31f2-5d02-4395-a6d3-1ff052de0925	hmac-generated	fc3e583c-0722-4ee1-aeef-70af67cf78ba	hmac-generated	org.keycloak.keys.KeyProvider	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N
3e77e337-8906-423d-8167-932bfc8a09ef	aes-generated	fc3e583c-0722-4ee1-aeef-70af67cf78ba	aes-generated	org.keycloak.keys.KeyProvider	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N
a18944d9-7428-4469-b618-304a4a7ac3ea	rsa-generated	89f9614c-27fd-44d6-b07c-c527669d1d5c	rsa-generated	org.keycloak.keys.KeyProvider	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N
89d5061a-3821-4961-b99f-6c961bddc66d	rsa-enc-generated	89f9614c-27fd-44d6-b07c-c527669d1d5c	rsa-enc-generated	org.keycloak.keys.KeyProvider	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N
a9156201-0f8a-457a-ae08-dec4d95f1d4d	hmac-generated	89f9614c-27fd-44d6-b07c-c527669d1d5c	hmac-generated	org.keycloak.keys.KeyProvider	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N
632d5545-ade9-40c3-aae0-7e066899be06	aes-generated	89f9614c-27fd-44d6-b07c-c527669d1d5c	aes-generated	org.keycloak.keys.KeyProvider	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N
9fe9d9e5-6500-4513-86c0-1d7932dcc451	Trusted Hosts	89f9614c-27fd-44d6-b07c-c527669d1d5c	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	89f9614c-27fd-44d6-b07c-c527669d1d5c	anonymous
446cac3e-4e82-45a8-ad8a-32b16e1413ba	Consent Required	89f9614c-27fd-44d6-b07c-c527669d1d5c	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	89f9614c-27fd-44d6-b07c-c527669d1d5c	anonymous
95610d7f-793d-4dfa-97fa-9a7388996c3d	Full Scope Disabled	89f9614c-27fd-44d6-b07c-c527669d1d5c	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	89f9614c-27fd-44d6-b07c-c527669d1d5c	anonymous
6629135b-58dd-4576-abd3-525633640c42	Max Clients Limit	89f9614c-27fd-44d6-b07c-c527669d1d5c	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	89f9614c-27fd-44d6-b07c-c527669d1d5c	anonymous
cd084044-db95-4f6d-9b18-f05fbec7eea1	Allowed Protocol Mapper Types	89f9614c-27fd-44d6-b07c-c527669d1d5c	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	89f9614c-27fd-44d6-b07c-c527669d1d5c	anonymous
dd02d553-459e-4004-a08c-0355435313c7	Allowed Client Scopes	89f9614c-27fd-44d6-b07c-c527669d1d5c	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	89f9614c-27fd-44d6-b07c-c527669d1d5c	anonymous
73da24b2-1a41-4acd-8179-982de95ca206	Allowed Protocol Mapper Types	89f9614c-27fd-44d6-b07c-c527669d1d5c	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	89f9614c-27fd-44d6-b07c-c527669d1d5c	authenticated
7e690e78-1cfe-4261-912f-ba03ee983bbd	Allowed Client Scopes	89f9614c-27fd-44d6-b07c-c527669d1d5c	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	89f9614c-27fd-44d6-b07c-c527669d1d5c	authenticated
\.


--
-- Data for Name: component_config; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.component_config (id, component_id, name, value) FROM stdin;
9285c6cc-0462-4b28-8ca1-46666cf0ef21	ab9a80b4-cd88-4a20-b6db-7694b91c204d	allowed-protocol-mapper-types	oidc-address-mapper
e13dfc98-f8c5-49cd-9389-9c7bfd7ef85b	ab9a80b4-cd88-4a20-b6db-7694b91c204d	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
cc4179d8-9d5d-4564-9c5c-6ea8130556a3	ab9a80b4-cd88-4a20-b6db-7694b91c204d	allowed-protocol-mapper-types	saml-user-attribute-mapper
05bf8ccf-26bc-422d-93b9-f6f6a3606a45	ab9a80b4-cd88-4a20-b6db-7694b91c204d	allowed-protocol-mapper-types	saml-user-property-mapper
a82e1e28-1ecf-4a58-b2ef-f15b2d0ea8da	ab9a80b4-cd88-4a20-b6db-7694b91c204d	allowed-protocol-mapper-types	oidc-full-name-mapper
d3da41a2-d62e-4128-9133-60e7b18773c4	ab9a80b4-cd88-4a20-b6db-7694b91c204d	allowed-protocol-mapper-types	saml-role-list-mapper
a3522e9d-c188-4236-9835-e8fe0eb1f260	ab9a80b4-cd88-4a20-b6db-7694b91c204d	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
36eaafee-5ba4-4058-aa90-32dd891c5a7a	ab9a80b4-cd88-4a20-b6db-7694b91c204d	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
3cb3742b-af7e-437e-9ec7-220968bb9550	64a9271e-d7e3-41cb-b5a4-6d3085932ad2	allowed-protocol-mapper-types	oidc-address-mapper
614527da-a90e-46d1-b899-7075ec69d74e	64a9271e-d7e3-41cb-b5a4-6d3085932ad2	allowed-protocol-mapper-types	oidc-full-name-mapper
58b0d70c-241d-404c-b614-1d6c7ef589d6	64a9271e-d7e3-41cb-b5a4-6d3085932ad2	allowed-protocol-mapper-types	saml-user-property-mapper
df1ce0ef-a622-454b-997a-f3578aca50a0	64a9271e-d7e3-41cb-b5a4-6d3085932ad2	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
7c989f22-e94c-43f1-86f7-b09bdab0c9a3	64a9271e-d7e3-41cb-b5a4-6d3085932ad2	allowed-protocol-mapper-types	saml-user-attribute-mapper
82792673-7641-470f-8829-666983f3ced6	64a9271e-d7e3-41cb-b5a4-6d3085932ad2	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
08979a11-861a-4e29-b9a0-639e62377e47	64a9271e-d7e3-41cb-b5a4-6d3085932ad2	allowed-protocol-mapper-types	saml-role-list-mapper
d8fbd458-bbee-4d77-a3f0-479bfbcf67b2	64a9271e-d7e3-41cb-b5a4-6d3085932ad2	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
43570e7b-4db1-410d-b2ad-0ece1b4d6123	07bc91d3-c535-4e06-b9b4-f50950754ab4	host-sending-registration-request-must-match	true
6ec62107-411f-44af-a541-f771c4e3c98e	07bc91d3-c535-4e06-b9b4-f50950754ab4	client-uris-must-match	true
e07ce6f1-a1fa-4ca6-95ed-0f664fac659c	56bdeb96-6673-435b-9c66-721704f418a5	max-clients	200
5e267d75-9785-4932-8b8e-e7c9cbb4bba6	6ceb63a8-48a5-42b9-9d61-0a1b04b9e69e	allow-default-scopes	true
a950c337-4618-4adb-b78a-bb892421a447	3e6ed46e-4477-4492-9518-8e1f4f100d9f	allow-default-scopes	true
351a53be-1199-4f72-97c4-28c380e3e446	de1653e3-d5ca-47a5-bb69-7f00e9e1160a	keyUse	SIG
b4b00b3c-0dc2-4f9f-815e-405a65ce544f	de1653e3-d5ca-47a5-bb69-7f00e9e1160a	certificate	MIICmzCCAYMCBgGKsE0nTjANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjMwOTIwMDE1NDIwWhcNMzMwOTIwMDE1NjAwWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDTbPQ30i3V7bHdyF/mWb1YpwS1f7O5ybj1G9fWFtg11T2Ev2G5TQ6iI0ap2JJTE9VS2F87ITVTr9V8WSb/Eo6UUmmZeoVJC5qcADFlTQhJAcCTk0KTgfxMJgjx++QwcNeKnJNpvxlouboOuOPJN3sxybI7/O08QB/cIokDxAZeP7LWS05v9Q1ZueIm/OUKqdAPJxswXK8yWnjfnxXPdTetJQgrsmWOYheAG1S/hUpeySSwtN/8KNzawQrz1E9WaLd1ppBtPVVSfaYEX91vLTErztI67McLGZY4g5U5Gq4qVc4pzjbHnaojmAdmiE6ZgG6LPS17en+ciAyovSmIyFpVAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAB/o2OzsxeBhJc2eKtVLrBemEX/GerSFe6baBKUMxJ6LuYDuhxELmB4Z2Pcdati0SlGD2ayqFvVd6cWxIxHjkgeLnygdl6lte1+pIT7IPafaf4ye42QqOe+klKEH6dke7ZhsdBNs9C/e30TWttHxhXrmA5jZEshmlaA+xxKTAMd5bKOjwvOXsrMhC4ZNoQyottQZw5fQVP2tewa6bSxClK+9MCgEe1hQE9iq4yiIb8pzDVoLAs4l+y6lglKbln0tu4AdnsdlQTMiFr4aITw6XbSwDJ7BOn3YGn+kwtjX9+4gGybOAZ7JWNDmFSqN1nYtLjDfIC32Tx931URAXNw36hI=
e7050fc8-2b95-4482-9f2f-a007eb638b48	de1653e3-d5ca-47a5-bb69-7f00e9e1160a	privateKey	MIIEpQIBAAKCAQEA02z0N9It1e2x3chf5lm9WKcEtX+zucm49RvX1hbYNdU9hL9huU0OoiNGqdiSUxPVUthfOyE1U6/VfFkm/xKOlFJpmXqFSQuanAAxZU0ISQHAk5NCk4H8TCYI8fvkMHDXipyTab8ZaLm6DrjjyTd7McmyO/ztPEAf3CKJA8QGXj+y1ktOb/UNWbniJvzlCqnQDycbMFyvMlp4358Vz3U3rSUIK7JljmIXgBtUv4VKXskksLTf/Cjc2sEK89RPVmi3daaQbT1VUn2mBF/dby0xK87SOuzHCxmWOIOVORquKlXOKc42x52qI5gHZohOmYBuiz0te3p/nIgMqL0piMhaVQIDAQABAoIBAARonktnW1PS60rR4b1iq9Su/5A4+7qDzD12NBZk2bjTpmmqePiddKY+YJFBm27WjXP3CuinfQkZSUpuFyap+yWjdfbAlooVpHFyJ+rcw++2dOQkOwxANrkwG4R6PsGg1L8b8Tn7BWYI5hTmV5T2TnVqZzL3h6CM620nyN4hJrXX31Khr/9yRpS47uYNTZJLy3vBZAlv9cjguYD9rlZTdD9//i7H2Fqy3e2dIPfITK4++u8cXEXbhL1MoBECeke9VN32SIVykv0KAhCqxHtSgQTveYooUy37ttqF00PgwWLRh7ekxiPnzSs0VznSdeRJlRak42xGGdU4aVROSz3gj6kCgYEA7OBR2DdNaF2nrBVUHcvJxui+rvotWIpDbKbgCH4rz5n7UN+YdjOry8mhZJ0q53DUD5Y69yvJ9SQ7AZiqa8KpkYT9qCGBFxjGOW/rG9XZnQlG8xp+F5+KMC3sXFNxtHI+uvtl9qZrjF+xUu4shOR6PdadpcpixrXZ3ZqFVUwvgj0CgYEA5H6g8MGCcGR9Dzd6PDqzbjBQI99j07KQJYR4bh5DYe7Oq92me++oxMsGY0uf4GpcNErMcw2ClrJBh9sJzS6Rf4WSykewnAZLpNbdOfjN4xLvz/WjNu0CJvn+ht2h3rbfYoN4uqPeSdlJPLibyHFrRviIudGO99SCttv1sXSJMfkCgYEA6afkU8lJW/d2G7L2RGXu5XxUC7nP3RSVqal6OCZtBN05H+9PLtmNTvnXb63w0ldjdvSTuZjCIEPayJpVEPL13Qs4Qw65jZTsg1+4pL5vaA3spISveJHw99F04yWUdZBz/HFbhM9oKxGBkvJJi9TsF++Jli5q/OQIwiVX/GmoO2kCgYEA48CEUvXPOpAvR3eY02f/aZmmh9sm+eBGZd8A22I27+rE/fJU+7wKkP19RIQgu+PIPCdCWnvg6qyLPQtLXcVJKb7Obem/VAXCaG/y2tHxBqHdf3/sS9c6CfetiGhp7ECDsQIy2HZ2H04B/hBU1xqWP4uDXRRQWR7tm67r2xG5nqECgYEA3Bvs3zLxO1xTklitlRyqDWeRPGhwFjUi6td2fkFSJTBLD3ekv+CogZFKmWv0TMCLQOzDAgT6/SOrA+flEQrIg1AByz48kqIEeR3rIN76U5GFvRPYHyxQT1YxBRkpNmJTemz+Q+EhgIZiEz12+Xzm94evYOt7YsIyqwZFj1ODzKk=
e2bdaf4c-bf56-471c-b775-614271bf13d5	de1653e3-d5ca-47a5-bb69-7f00e9e1160a	priority	100
b020d9f8-e208-4f17-bc2b-a7e1d0eae3c7	036e4791-e042-4516-a99a-c81761ed9a00	keyUse	ENC
d6bedcef-28ba-4068-a599-3f8acd72be4f	036e4791-e042-4516-a99a-c81761ed9a00	algorithm	RSA-OAEP
36258ed2-c6d5-4d54-afbb-25f93b0f8afe	036e4791-e042-4516-a99a-c81761ed9a00	privateKey	MIIEpAIBAAKCAQEAxK//Ontf618ZEwpuif2kLglMdxqFkuoHZA3pxdsoZ8nSJ5EuKpmgG36dhp+4ktVfS8OPJvxEvN/8F/sNWZOWeMD+ky3CnXso/ofM73CYuigRrjNtflOiIArdPGcees2zDN3Od1egLYN8Xq0W9xrafdptsecvoFqGkWY9Q2hlArMtzUzBAQZyYY/pMKiiy/dB/5cKhmvxZVq/kI9d9bV0VjIZDfRAn5pjpgQl1n9LALD0ZtgaUX1Mjzb48UmADxonjPxF4MQKW8UxvN+PnqySkPP9rWVSMB1LVsk48DKVoZYefeLVDM3Otpdy9qILLCebsADx3UtIbfIW1Jt92tsBPwIDAQABAoIBADIlHsCFuNg0yBtDIxh6OpPkGgMZl2lz0guPwoSHkd8YJMSirm78+MVZvAaZDQEedivGQWZylYHD5y0j++zVBF1n8t+KIJ5uym5OKBD5l26KjatogCdfYiuiKXJfZfVwIRCMMjnZ9MP1zE0E+yBnz8GbYTEknE9ZFQrX0pGveWU835eEuVczG+GW8WGbhFX06B3rttKEYC4u7eV+WW8SFikn6gF8QgjFawluv9MDtmq0EjjMGTdwd7DYgq7yER+BxyrulZ8vLGpTM060fbXEuHTAqZFpIfEbDEpxUIkN0jpx48qYxQm/aH/7pohzS6ThEGkiDLVqeTxJ3CMUhSAh5r0CgYEA8flz8y288NYwhbKKjPQaOiQGIGmiRnH2mSrjFC5Wce+tRGnXN8wleheGcZlHWpmcNbyDqYD1d+D8SMfw4d5TpcJdZjyn9JweQs76q7LhXG0QVRRF5KWBXOGZRSzgF3CBvmZmHGZY2SCBsWNdAJXy5mjLtO8w4OGoJmsNX07oFnsCgYEA0BaNUxfh6H6W6B16e5YZgPJMVOCIYycxERYJF4Z94usr8LJEOBHM/7LSWgWX1Hr91xj6JJjTiFQa14P8DUFY4PFVkxM/ZktEkxvUx/Cg+WX6GYg4U+HCGVm+rDagb9yr4PoNtvMDBFHRSqXdcXvJ0hJtBL0l5LC6yoPxsN11hw0CgYEA8dKzJlyXbrkgVP1RqfxqGaDW3p2zJ2PNZtWNiyWkDsnRB4YfKAxFh/bqzay5urm5e5qimkvfBac7eiEXgyOFxSqx3PLRh4xeUbY4nk9vGceFgwF3uEIPPpvXaWKnscUmZPpBIjDGqnfqJTWARI2W6t3gdzp0Rso+GebyvkVFfPECgYAnW9ynVuEYBLmudcTxE1cHmMzvUPj7KDKTtMNLe9jbzveks1NL97H6u2wmMuChBBJ3WPYXbgC0zJO3J/PI3g9dnMg+5WkchCgYWy9IEi+gfLVtluJwm0cHXH5APKTkA61PBH0LmbN8Ya1gnTbhfDSUU3/jYZOFShhKreJNPdY5EQKBgQCqsGxZtKcuWZEFYvArC7aBk7aC17chvak96sbbrry0w5cKZ/aVWpb+J4thfQbCn4m4WelIrf6eZ42kPTtDgN/BUqFU4I7OCgHXbPZuAORcouEe7/Dpxrniaia7dY8q/mq9c+ie7YdUHiq1ZZAkqMgLVCC8VxK9kZRhNjjshj/Vjw==
a2d7e12b-fb79-4bba-8c66-ddcda10bed04	036e4791-e042-4516-a99a-c81761ed9a00	priority	100
5dcac4ed-63b2-4c93-8298-40d71697e199	036e4791-e042-4516-a99a-c81761ed9a00	certificate	MIICmzCCAYMCBgGKsE0pZjANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjMwOTIwMDE1NDIxWhcNMzMwOTIwMDE1NjAxWjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDEr/86e1/rXxkTCm6J/aQuCUx3GoWS6gdkDenF2yhnydInkS4qmaAbfp2Gn7iS1V9Lw48m/ES83/wX+w1Zk5Z4wP6TLcKdeyj+h8zvcJi6KBGuM21+U6IgCt08Zx56zbMM3c53V6Atg3xerRb3Gtp92m2x5y+gWoaRZj1DaGUCsy3NTMEBBnJhj+kwqKLL90H/lwqGa/FlWr+Qj131tXRWMhkN9ECfmmOmBCXWf0sAsPRm2BpRfUyPNvjxSYAPGieM/EXgxApbxTG834+erJKQ8/2tZVIwHUtWyTjwMpWhlh594tUMzc62l3L2ogssJ5uwAPHdS0ht8hbUm33a2wE/AgMBAAEwDQYJKoZIhvcNAQELBQADggEBAJpgkX+yMObz+l/Rx5mBxL4h7wIGfarnoYWkO/0JsSFyrcVJay6FKTEbFrjD8ffvAtPo/fWsc8XFbd3o3JFYA6mmpFSPwljwDi1UpEdAHqwpQvx0CD5tS2OMC4up+4Xio32nMv2xsFZN0ExTL3X0+g1ekJkdgTvTcatP7euPyqd7QC8IaxChJdReI/xk45Vk7IOKvvvQKw8l28fRIwRh2om8LGhPE441hf2QXIaelnARrat5nNsaBWqrROdnJI/Jq4IOhqSUZKHpH1jAwqDDJ9+pCIlEhpj1ySLg2nEHN02kkhPyZkkJz8tMnVxZFcyAWvVom+qqlvxVQMqwEeUAxAY=
8d7933ff-38e8-4721-8aa1-82d349b07e47	ffbc31f2-5d02-4395-a6d3-1ff052de0925	kid	8d081216-4797-44eb-8c76-bccb02525653
ab9ea0e1-2b14-4b53-a124-916e37d255d7	ffbc31f2-5d02-4395-a6d3-1ff052de0925	algorithm	HS256
49ec67f6-72c6-4218-bc16-7e76b292aa6f	ffbc31f2-5d02-4395-a6d3-1ff052de0925	secret	iedwlbThGiZbKNOsFtHekaZGjCTIff5j9l3U6Csqt4BjquNIo-28QgUlbr5UDChO7WJe3HR0oDc8IStkZ3SK0A
1579a822-ecd6-4aed-a18f-f0b6b6e26e66	ffbc31f2-5d02-4395-a6d3-1ff052de0925	priority	100
c4c6b75e-93ae-48b7-9c18-7ed1a413c533	3e77e337-8906-423d-8167-932bfc8a09ef	kid	27842ff3-d23d-406e-baea-d3d35f8e1aaa
3e535f4a-b492-44bb-bc90-3fd074b78ee2	3e77e337-8906-423d-8167-932bfc8a09ef	secret	TJ6NGHZbXVGseYJ2V9GzIA
785751cf-ad98-48c6-8124-cf621137c888	3e77e337-8906-423d-8167-932bfc8a09ef	priority	100
3b126464-386f-479c-9ff3-cc88f03ef59a	a9156201-0f8a-457a-ae08-dec4d95f1d4d	algorithm	HS256
e81e4771-9ccd-456a-8f7b-02c98661b2c7	a9156201-0f8a-457a-ae08-dec4d95f1d4d	kid	54d81dac-07c6-49ec-bc1d-919e12b1ca91
245e1304-19b9-47d8-af57-f6ba0f28904a	a9156201-0f8a-457a-ae08-dec4d95f1d4d	secret	8IHMV_JUWQE1JVCZ3YndKOFDDWlDotdXv_p6OGRiIDKdjAqkUsM6Y64Nfyb8OMsye1vTZaFO0r6jqfFVq0Id_w
a3c536f8-adf9-4c66-9cae-2cfd134460d8	a9156201-0f8a-457a-ae08-dec4d95f1d4d	priority	100
28c02d74-d06b-49dd-b17b-db8b8b414b37	a18944d9-7428-4469-b618-304a4a7ac3ea	priority	100
4f68d638-db16-4527-8a9b-ac250b9cdf16	a18944d9-7428-4469-b618-304a4a7ac3ea	keyUse	SIG
1c3a7f93-6684-448f-8c1e-57099b4697da	a18944d9-7428-4469-b618-304a4a7ac3ea	certificate	MIICszCCAZsCBgGKsE4YXTANBgkqhkiG9w0BAQsFADAdMRswGQYDVQQDDBJyZWFsbS1wYW50YW5hbC1kZXYwHhcNMjMwOTIwMDE1NTIyWhcNMzMwOTIwMDE1NzAyWjAdMRswGQYDVQQDDBJyZWFsbS1wYW50YW5hbC1kZXYwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDF6BM+JR+b/Zafy9DJRxORVckIf49gWL/M0lb9cpzIDxjqz1CSulEjcVZRNK5GUeM1vV/srj61WDealqxaEuy2XzAvGkuHXd76TBont+SAQO7+XA/vMjw1Q9KI+Lg0ZLnfiG8GdhzSQbtojUNS66M75XgBKNr4l2fHt+Nkpnj/Ykr+xpb2cNgJNjRS/ER98EETWL9C2df8eST+KasGpWYQktH5GE/AVcgW4jCBRPDn4btS8ZNPter1ypRe94m26yXGecnxV3Jzz0x4sicJ2eAaLv2IqlVfT2XyOQZlpmDE1DL9hZEUwlO9Kp42tA+qm7nHSRh+jq5q+rTnhycxa63rAgMBAAEwDQYJKoZIhvcNAQELBQADggEBABd2Bo1qmp3s2vAak3J7mYVmQEWAoqGn0TRi4oE/oQDTjoSAqztfMi00iNlrI9BcZGp0wKOmuMCQE3ntEzQnRdJWWdmEC1RMLa2lKd/I/1DkFweF59mllgT9y00f6ruboV7osJ6jUaNBTgPdFUEkojo8tUulit2kdv6PY8Ma3w000uommX8/tZ3sm6ndNARuV8GEVS/NG7DdxuptkVfz6kkVuE8YAmB3Bf5BNfIBXybAcRfCFeglkdeTvKp/I9TQzd8MSN8bAnZLPGRMo3RjCGOoBdIgpLwRNp3+C2eQYk3A7yvlh5hD3t8M6NHlZ2PQMQ4VNAqV689GRw5gxqIb5K0=
9cf755f1-9fec-4767-a8b9-30d11cba8b1e	a18944d9-7428-4469-b618-304a4a7ac3ea	privateKey	MIIEpAIBAAKCAQEAxegTPiUfm/2Wn8vQyUcTkVXJCH+PYFi/zNJW/XKcyA8Y6s9QkrpRI3FWUTSuRlHjNb1f7K4+tVg3mpasWhLstl8wLxpLh13e+kwaJ7fkgEDu/lwP7zI8NUPSiPi4NGS534hvBnYc0kG7aI1DUuujO+V4ASja+Jdnx7fjZKZ4/2JK/saW9nDYCTY0UvxEffBBE1i/QtnX/Hkk/imrBqVmEJLR+RhPwFXIFuIwgUTw5+G7UvGTT7Xq9cqUXveJtuslxnnJ8Vdyc89MeLInCdngGi79iKpVX09l8jkGZaZgxNQy/YWRFMJTvSqeNrQPqpu5x0kYfo6uavq054cnMWut6wIDAQABAoIBAAE/PLs7UvALvNNsj0EvVIQLQcOyChIxWGWSAAN3TEWbA4KDc4Yj1B8OMaxILUTBHdBxQQl7OkJO1qlU/Ycf9eDtQcOEH0f/pkgI5ojoxdJ832Yrq0l2jzCoQVF0As2CeYrhUrlj45zjXGHNyzWpyX2GXcXhDMlQZ96e3dF203P5QaU+C/4E+Ox565sO9SqPOVfWYVrVNPjqn4GLTWwyfs9HgcwnCXLkYqiDuEAZ2q0LSESibZR/OcY0q1aYl9HAgQdChz/VFDwHbkp1gYYIs2uGxq0BDp7hWBrUvBqauTcCLo3H4rp+13b+Ksq+SwVJurLCnmQqhXiFu6aj7piHUQECgYEA7rcVSWElhQ0zt/ickR9lk04Y4BSTeg5gf3oumngvXKDTgNPjxo5szWohb1O+KlwSCieaos5OnGBAFJGNpvNBeM0sMX/wMrmlOqxw6zKclFCPRxp+IziPBaNKk30O+h3vOXriVCH1qO7UTjoO2y2RiEZZQpskJW93JKgmf9AfTT8CgYEA1DyMKy4zp5PAi6/tuyqyxMmsAOiNzwjMGqqfmhO1HhAzUEsus7BrNM2j8OAtcBXQplt/moAT1SLJTk86N/tuzQCLcxU26YyspWiaKOD1yDjpcm8L9Mm5rzIwhq78ePGKnl/xzPLFT88Yv5l+NRo2mbO4zedWZCS+2dEcswVO+FUCgYAFk5tYsn9I420Q4CrwLPaXgVyNZ42mbU2NzNHn4+TfWhZ2zPxmOA+MoO0VB7TKkooNWK2BHiax+oGkuV3Ax7AA3XIp2d5HoRSeEUc2B2GnCVa/h3vo4LpenFjypxpBd4+OHTPKWVy+zI2DIlIUhrT+pP4S00L96OO0pIT5uWfxywKBgQCbOjGWv3L+RN2WDTWGKEOaFJqeC3j0jM2jT1nuOi3E8DpnpqVgvU6bzy/x1DTHft0dX1yj5rsarxenE+FT4qz2eUiFAmYoSUBYtnnSHj8Fk0KC9N88yXHqxn2hdEiqOJeXIDtdF87igWjrcdtSAXsfzcgDqQiRtCJ2czBXazGIBQKBgQC967XkZ4BpxceytZYE90L1wO9RpRR269VSlY9E7OKhGC98zDBK0eCajrFOwhKhdxglDRMTVX1WhQA3j2ws1hz9+vyCkXvY+0NmZm6gELHuR93R9aCKFM4DEWBgkhC0cNEU6yCS/EX2CD5wF6FlXTCJE3g1PUMtDOcALXsBeICYVA==
70a8deeb-ae0f-4286-9ff5-c2d9995aea6e	632d5545-ade9-40c3-aae0-7e066899be06	secret	-WU2H0J_7-BfUtELD4pTVg
95d39e7d-9898-4bf1-9716-d8d4f156f010	632d5545-ade9-40c3-aae0-7e066899be06	priority	100
1f60fdfb-ac52-445b-b0f2-428a94fc8d35	632d5545-ade9-40c3-aae0-7e066899be06	kid	c73dbba3-019b-4850-a105-2895ccaef6dc
4e648ab3-c9a4-4bdb-9671-769450817958	89d5061a-3821-4961-b99f-6c961bddc66d	privateKey	MIIEpQIBAAKCAQEA3m84SBZW4QfiNDHp9htkwc6kTRHd3PcAyiJm3yBzFF8EtDYZzVrfd5aXEdzH+PWGJfkJRQ1ZGYXO4R+CXoThAEXuar4fxmETFsQObngfQTL1wli8ZlyGxwZXPgIYPeyRYHinAuct6yFwqrU1mrmOthl5UHSSiPr3qVhHdVydmF1Z+FNrIofhQmF8XTovwpTZfxsqNJm8e3gMWKmHaibep4uyeDPOz75iTsWhun9608/ydLDYa8l81Wqd7Ar10Yoob7yfpwsEiC5boM/MDmOIo9gig+34FRTLEspJl8lTxHPMeigFFj+T1RXiGbWSTeFGyVU7wK6MvA6BjrC9TF8A1wIDAQABAoIBACJJxcTDANB+jR2bpiOkdGM1zfj9bJRv1QTTycTSdTrqLMj04ARaYkFGivIbdQ4JFw1KduXYmWgISuxEFa4pUJ/gp9B3j2FevHkRbuITjUnZJ642TJCA6dvLO+6YlxEQoxZacvBNH7RzurDt7x3zRa8HfT5sQdxwx3Wo4JcUWEzQqYIYsO1NI0z7FM3zJXA7XLt/ba2NnTYGBK5t28CFz7O8gI+g3OZ5OxNC7EVLeXgeK2muuf6zM6UgxTOwFqMCxg2KbJ3iNFn17+LO0dJmoG7A+Mqiex1FJE1gz7naEZayuEAlykpXM/I5o1B5iw1b7OvDiadBtTUcEznfuuMXaP0CgYEA9OW+3GSZhPKobzNtwss7AVOjnkJb3aX6TFO8dkOBXfACnH+AK+JMLCq2UFvUcvWvVr0zbfYkR3qKt4mchUVVS3NpPloGcBR9nmS0SnrbajgvAbrlI/vgbyg1NDutxhOifpZdgxyC3GOrU/fHukVhvgCs5RHQn42ea1CReEE5yrMCgYEA6ITFabuPZTcrz2vCr2YXy8GxUWkkuejiJJvWxpCeLHwVAdkXdrNC9pzu1LCwNMSupv82RMFOb0ME1nJP+PBqtazIf7BOObRJv2HsLg/W9OQRPMVabjYpdf2vn+3LQ619fh2d0l08Yt8pq6NvIRQ392BopeBpHxf6rUwZMbAnU00CgYEAt/Y1Lz1qjRHzMMVDOEaN5ms7x1A1zgQ2R0vHkFcZQMVgUMr+LvjpG2xiU28H4etocRHjJkD187O7rC91Ii946Rqi/jGGEH9z9Z3USPaNB/d4aCZKpoM4jod3n+sMmHR7Zffx+uFZ88/zDTpeEDC6QY4kUlbVjmVFkXuOKvVkQj0CgYEAxjFV7FgZVFcAmt7ppYRKSD2jnRcYXPcqu7WyDrL6gESLy3V2UJFgRMFjuhuZNoFntaTYZya2apQfrYLms/SJoK3UgTKQmxMXwXAAVwrdWQSFxN0bxdGY6aABcTrKBbf9veAUxFkAve4moMzATtvjEJM24B02vXA9FjZFS1sxSgkCgYEAuEOMyqQorV7qASHgTyeM5h8AS13cHnax8eSePeEza0g3JeOWqt+WH3AaNs7fFLoeoEioora+6laLsLc556T6BCMZwM9gxX3eA19Rb6qsrz4jzXLw3sAGtjpqzUMYsHX3HRLUhFQBaIAJUg6ZbUxue01lR67nsnDYLkOU0e8uqrY=
ad6fd825-450c-4425-888f-93e537684fcf	89d5061a-3821-4961-b99f-6c961bddc66d	algorithm	RSA-OAEP
d8f85379-9913-4784-9c08-967e2dc34f10	89d5061a-3821-4961-b99f-6c961bddc66d	keyUse	ENC
6c3b914a-d14d-45b0-a805-bf72f9f55a43	89d5061a-3821-4961-b99f-6c961bddc66d	priority	100
b3061043-2f41-4154-98d7-1b4b8d3f8fb7	89d5061a-3821-4961-b99f-6c961bddc66d	certificate	MIICszCCAZsCBgGKsE4ZFTANBgkqhkiG9w0BAQsFADAdMRswGQYDVQQDDBJyZWFsbS1wYW50YW5hbC1kZXYwHhcNMjMwOTIwMDE1NTIyWhcNMzMwOTIwMDE1NzAyWjAdMRswGQYDVQQDDBJyZWFsbS1wYW50YW5hbC1kZXYwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDebzhIFlbhB+I0Men2G2TBzqRNEd3c9wDKImbfIHMUXwS0NhnNWt93lpcR3Mf49YYl+QlFDVkZhc7hH4JehOEARe5qvh/GYRMWxA5ueB9BMvXCWLxmXIbHBlc+Ahg97JFgeKcC5y3rIXCqtTWauY62GXlQdJKI+vepWEd1XJ2YXVn4U2sih+FCYXxdOi/ClNl/Gyo0mbx7eAxYqYdqJt6ni7J4M87PvmJOxaG6f3rTz/J0sNhryXzVap3sCvXRiihvvJ+nCwSILlugz8wOY4ij2CKD7fgVFMsSykmXyVPEc8x6KAUWP5PVFeIZtZJN4UbJVTvAroy8DoGOsL1MXwDXAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAERNquFweQknWG+2vayWcb+J419HJQ4BYqMX+VxMwqImBVGPAbMRUXmdXMTz514jESbeevnt/R5dSmU9Az49PMrenZPkz6mj0YOOGKYpjZoqgrZK6epDqB6PcZLOWXzEmbbEHJ3M0SqGurnaL3/AyYU5b5nKCjrDnzPRkxMFPV/PKr4myfDyVsv8AGRAPQIeVEjad3lP1GSYB8tNnZD6yTmDo05Ctqws1SHPWeBM5ACLKJamodjGLb/pQ3UAXTpstHaxTQEaOnPacJjdqy8HxOZaCRGOj0LGu/euCkYKA8pPuG9dChL3oADQPeXBTi/nN4sfSYhXQDNKtJV+vxSRnpo=
0958fd83-cc72-4cd4-8ed9-10ec353400b1	9fe9d9e5-6500-4513-86c0-1d7932dcc451	client-uris-must-match	true
73020aa0-b412-4afd-908b-611b89866a8f	9fe9d9e5-6500-4513-86c0-1d7932dcc451	host-sending-registration-request-must-match	true
67e783ad-7058-41b5-9536-16a8f8630bd8	73da24b2-1a41-4acd-8179-982de95ca206	allowed-protocol-mapper-types	saml-role-list-mapper
d4908e9f-525b-428c-863e-53a1c651f165	73da24b2-1a41-4acd-8179-982de95ca206	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
5c96d9c5-a9cf-4c7e-8323-f8657e1f7701	73da24b2-1a41-4acd-8179-982de95ca206	allowed-protocol-mapper-types	oidc-address-mapper
79b0d8f5-b5a0-41cf-bf2f-e8e33a39804c	73da24b2-1a41-4acd-8179-982de95ca206	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
20d52a25-377f-4be2-9cbd-2a775e032bc5	73da24b2-1a41-4acd-8179-982de95ca206	allowed-protocol-mapper-types	saml-user-property-mapper
04779953-db42-4cce-8b57-243b2351111b	73da24b2-1a41-4acd-8179-982de95ca206	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
cc8107b6-bb07-4210-9f6a-935e9599fa08	73da24b2-1a41-4acd-8179-982de95ca206	allowed-protocol-mapper-types	oidc-full-name-mapper
b5e765f6-3131-4d3f-94e2-8c715e790fbb	73da24b2-1a41-4acd-8179-982de95ca206	allowed-protocol-mapper-types	saml-user-attribute-mapper
ef71b688-5f34-4f8c-844e-0b3805af45d9	dd02d553-459e-4004-a08c-0355435313c7	allow-default-scopes	true
d1ae46fa-508b-48bb-995c-02b22e79c2e7	6629135b-58dd-4576-abd3-525633640c42	max-clients	200
981123b6-4428-422b-897f-fb2c49550c30	cd084044-db95-4f6d-9b18-f05fbec7eea1	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
1cd8cbaf-20c2-414c-9188-1f18ec6d3035	cd084044-db95-4f6d-9b18-f05fbec7eea1	allowed-protocol-mapper-types	oidc-address-mapper
6add7112-b274-425b-9ff1-bb05fbf514bc	cd084044-db95-4f6d-9b18-f05fbec7eea1	allowed-protocol-mapper-types	saml-user-attribute-mapper
b4be49c5-b339-4dfa-99e7-bda0d8777caa	cd084044-db95-4f6d-9b18-f05fbec7eea1	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
9c48aab0-608b-4b06-bb61-71cc3c7cf517	cd084044-db95-4f6d-9b18-f05fbec7eea1	allowed-protocol-mapper-types	saml-role-list-mapper
a2aeb2d8-9c98-4bd8-8ac3-80f39b5a7321	cd084044-db95-4f6d-9b18-f05fbec7eea1	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
343e9777-8b42-4764-99a6-730315f572a2	cd084044-db95-4f6d-9b18-f05fbec7eea1	allowed-protocol-mapper-types	oidc-full-name-mapper
d192f7d9-015e-4258-8297-d77026521ab5	cd084044-db95-4f6d-9b18-f05fbec7eea1	allowed-protocol-mapper-types	saml-user-property-mapper
f00d1201-ba99-4afb-a19d-145e7ab468bc	7e690e78-1cfe-4261-912f-ba03ee983bbd	allow-default-scopes	true
\.


--
-- Data for Name: composite_role; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.composite_role (composite, child_role) FROM stdin;
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	33f6f7d5-d60b-416f-98e8-80737b6fc8a4
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	be6d98db-db6b-4646-bafe-eb60bfc00070
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	de30f2ce-1830-4785-af30-d484e6996d5a
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	3dd5882b-3d46-4129-a767-b7a196fd6989
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	0d139e77-3c35-4cb2-a4e2-e9df0b678bd4
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	fc5ba1b3-e7ca-4594-a603-79d03278d997
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	56a8ea35-23d4-4749-9381-5696c491b2f4
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	86142502-b239-4001-a719-49d3e7175cf7
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	12e73d16-7b35-4715-a7ba-ffc00dc8c187
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	d6e50895-e9db-4650-9c89-89fec312dec5
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	66fd55bf-201b-45f0-bd88-d809a5426ea3
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	df34f42f-bd2e-423b-a202-31e5f23d4f3f
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	b8fb4a21-0096-494b-93a3-69ca367ed56d
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	c1f8be64-a602-4412-9969-3948a472074b
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	903f5c86-3413-426e-8a1e-44bc4f185233
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	5463c41b-cc98-431a-9a3d-7eafdc230337
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	675bdb12-e403-4939-8494-1a9581d1b223
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	936e0555-c2bb-4f15-9df4-a91872575200
0d139e77-3c35-4cb2-a4e2-e9df0b678bd4	5463c41b-cc98-431a-9a3d-7eafdc230337
3dd5882b-3d46-4129-a767-b7a196fd6989	936e0555-c2bb-4f15-9df4-a91872575200
3dd5882b-3d46-4129-a767-b7a196fd6989	903f5c86-3413-426e-8a1e-44bc4f185233
8d754a55-ec91-4fbb-90a4-1061f7104e34	beef3bb2-8000-4992-b0f6-86faafa3d39e
8d754a55-ec91-4fbb-90a4-1061f7104e34	76fa9fe2-9cd4-4539-b375-b3ac1e3d85b5
76fa9fe2-9cd4-4539-b375-b3ac1e3d85b5	846bf181-36f6-4539-a08c-eb154c715705
e67a0fd8-77d5-449f-ad58-41b4af1454f1	0f5d5a78-42d1-4c71-bdc3-2e7600213981
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	761ee8eb-e856-422e-a92e-aab073d92268
8d754a55-ec91-4fbb-90a4-1061f7104e34	5690ce3d-1ea7-4f4d-8cc1-2777667c0aa3
8d754a55-ec91-4fbb-90a4-1061f7104e34	a274a639-6011-41a1-b593-3088aea52eb0
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	84a57036-c660-46e9-8269-e65339589114
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	2c7dfd6f-8b67-417e-9ca3-2f2515133738
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	4bac61f4-8b7d-4d79-923e-caa7c51b2390
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	f35f20c6-1468-4f81-bc57-6336369c1f2a
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	f66ef8c0-440e-4110-9475-e6bf6921288a
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	323d8c47-6191-4afc-89f7-ed4da4f34171
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	0ea30235-713a-492f-8e55-3e786e3703aa
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	72e05d0a-f850-4c8b-a6f9-7fa865fa8c12
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	185454c3-3bc4-4589-b1e9-f0817dfd7878
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	4c38fb49-9048-44de-b22b-ba5195f737a2
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	99539257-f641-4395-97c0-1fce95793597
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	f9b9699d-be45-4f29-9a10-c937ceba33e2
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	664ecbb7-bfcf-492f-9200-311da3360358
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	6e7e357f-90a9-488d-8496-73d8799800d7
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	d797e279-0d71-46a9-8a9a-03aca51cd633
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	a27ff082-2d2b-4130-8e9a-bdd8eea1c286
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	fcfa2fc5-8e27-4f43-9884-dc750fcba5fd
4bac61f4-8b7d-4d79-923e-caa7c51b2390	6e7e357f-90a9-488d-8496-73d8799800d7
4bac61f4-8b7d-4d79-923e-caa7c51b2390	fcfa2fc5-8e27-4f43-9884-dc750fcba5fd
f35f20c6-1468-4f81-bc57-6336369c1f2a	d797e279-0d71-46a9-8a9a-03aca51cd633
4b0f62a5-156c-4327-9984-5381cd7aa440	df625d4b-3286-42d2-9ae5-531b78dadd4f
4b0f62a5-156c-4327-9984-5381cd7aa440	cd5b9411-3733-4d7e-9348-127592db0beb
4b0f62a5-156c-4327-9984-5381cd7aa440	836205f2-2323-46e9-ab27-121cb3593371
4b0f62a5-156c-4327-9984-5381cd7aa440	e003249d-98e8-4e0c-b11a-6161e53a2a07
4b0f62a5-156c-4327-9984-5381cd7aa440	6af6f895-1117-42f5-89d0-f823fdca15d4
4b0f62a5-156c-4327-9984-5381cd7aa440	e51f0b1e-547e-4817-bd74-335b274fd0c1
4b0f62a5-156c-4327-9984-5381cd7aa440	f11ee328-716b-429f-94f3-a885e06bb1b1
4b0f62a5-156c-4327-9984-5381cd7aa440	a8fdd752-e5e5-4c0c-a909-344192edc59d
4b0f62a5-156c-4327-9984-5381cd7aa440	fb640fe1-20fa-47d0-95fe-c773b55ab9ef
4b0f62a5-156c-4327-9984-5381cd7aa440	529e1198-678b-428b-864c-e4dc79c6b20a
4b0f62a5-156c-4327-9984-5381cd7aa440	13f279f5-94d5-4511-91e8-855870cd4882
4b0f62a5-156c-4327-9984-5381cd7aa440	83d5f6e8-816f-4051-b65e-71bd11fa28fa
4b0f62a5-156c-4327-9984-5381cd7aa440	0ab41a31-20a4-4e4a-a77d-e6e44f3b0122
4b0f62a5-156c-4327-9984-5381cd7aa440	8ea07074-4203-4e0c-b716-241b4475de7f
4b0f62a5-156c-4327-9984-5381cd7aa440	b99a6f9d-ab79-4dce-9b33-a414af25dafc
4b0f62a5-156c-4327-9984-5381cd7aa440	abdf3ba6-ca04-4c81-ae3f-d252d345e24b
4b0f62a5-156c-4327-9984-5381cd7aa440	174b809c-a0bd-4420-a65e-106b7192df47
836205f2-2323-46e9-ab27-121cb3593371	174b809c-a0bd-4420-a65e-106b7192df47
836205f2-2323-46e9-ab27-121cb3593371	8ea07074-4203-4e0c-b716-241b4475de7f
cc85fef9-25b9-4515-98ce-0be4a5df89ac	ea28a649-4409-405f-a833-47b993b90197
e003249d-98e8-4e0c-b11a-6161e53a2a07	b99a6f9d-ab79-4dce-9b33-a414af25dafc
cc85fef9-25b9-4515-98ce-0be4a5df89ac	cb51f8b0-0f6f-401e-8bc8-eb247e2b6fed
cb51f8b0-0f6f-401e-8bc8-eb247e2b6fed	0edbf0a2-3eec-4654-97e5-7f1860c7e2d1
62689410-a0e3-47de-b733-64783de4a697	cd5f6c22-798c-4711-b35b-ebea1cddd37e
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	885156fc-c918-4cee-936f-7a103591e8e4
4b0f62a5-156c-4327-9984-5381cd7aa440	4a490a09-61ac-4c3a-9119-045fe3bf0a67
cc85fef9-25b9-4515-98ce-0be4a5df89ac	8eb3be08-fd48-4e0b-8a58-7488f1a8f69c
cc85fef9-25b9-4515-98ce-0be4a5df89ac	9c98d677-c8f8-418e-8929-3093bba6d755
\.


--
-- Data for Name: credential; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.credential (id, salt, type, user_id, created_date, user_label, secret_data, credential_data, priority) FROM stdin;
4690dfc3-0579-44c3-92a9-2503eaf2375e	\N	password	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff	1695174962369	\N	{"value":"tXBCgRG1RnR3lWpUqIOgvx4WeDFLW6sR4NY39M2suJg=","salt":"6iMpBMeoCLRmSYHMQim2pA==","additionalParameters":{}}	{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}	10
98dbbd30-ae9c-4281-be41-dd314e5a428d	\N	password	065872c1-177f-41be-9ac0-3221146ad40b	1695175118899	My password	{"value":"qYjl9ebPBcLxR5VaQeY4E7LX8Y8ckA7Adi4BDmuE4Bg=","salt":"ls/4KedLIl95PaH9rg1KZg==","additionalParameters":{}}	{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}	10
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/jpa-changelog-1.0.0.Final.xml	2023-09-20 01:55:54.837313	1	EXECUTED	8:bda77d94bf90182a1e30c24f1c155ec7	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.16.1	\N	\N	5174953957
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/db2-jpa-changelog-1.0.0.Final.xml	2023-09-20 01:55:54.850268	2	MARK_RAN	8:1ecb330f30986693d1cba9ab579fa219	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.16.1	\N	\N	5174953957
1.1.0.Beta1	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Beta1.xml	2023-09-20 01:55:54.981061	3	EXECUTED	8:cb7ace19bc6d959f305605d255d4c843	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=CLIENT_ATTRIBUTES; createTable tableName=CLIENT_SESSION_NOTE; createTable tableName=APP_NODE_REGISTRATIONS; addColumn table...		\N	4.16.1	\N	\N	5174953957
1.1.0.Final	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Final.xml	2023-09-20 01:55:54.993197	4	EXECUTED	8:80230013e961310e6872e871be424a63	renameColumn newColumnName=EVENT_TIME, oldColumnName=TIME, tableName=EVENT_ENTITY		\N	4.16.1	\N	\N	5174953957
1.2.0.Beta1	psilva@redhat.com	META-INF/jpa-changelog-1.2.0.Beta1.xml	2023-09-20 01:55:55.174021	5	EXECUTED	8:67f4c20929126adc0c8e9bf48279d244	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.16.1	\N	\N	5174953957
1.2.0.Beta1	psilva@redhat.com	META-INF/db2-jpa-changelog-1.2.0.Beta1.xml	2023-09-20 01:55:55.180147	6	MARK_RAN	8:7311018b0b8179ce14628ab412bb6783	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.16.1	\N	\N	5174953957
1.2.0.RC1	bburke@redhat.com	META-INF/jpa-changelog-1.2.0.CR1.xml	2023-09-20 01:55:55.442999	7	EXECUTED	8:037ba1216c3640f8785ee6b8e7c8e3c1	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.16.1	\N	\N	5174953957
1.2.0.RC1	bburke@redhat.com	META-INF/db2-jpa-changelog-1.2.0.CR1.xml	2023-09-20 01:55:55.448199	8	MARK_RAN	8:7fe6ffe4af4df289b3157de32c624263	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.16.1	\N	\N	5174953957
1.2.0.Final	keycloak	META-INF/jpa-changelog-1.2.0.Final.xml	2023-09-20 01:55:55.461308	9	EXECUTED	8:9c136bc3187083a98745c7d03bc8a303	update tableName=CLIENT; update tableName=CLIENT; update tableName=CLIENT		\N	4.16.1	\N	\N	5174953957
1.3.0	bburke@redhat.com	META-INF/jpa-changelog-1.3.0.xml	2023-09-20 01:55:55.621605	10	EXECUTED	8:b5f09474dca81fb56a97cf5b6553d331	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=ADMI...		\N	4.16.1	\N	\N	5174953957
1.4.0	bburke@redhat.com	META-INF/jpa-changelog-1.4.0.xml	2023-09-20 01:55:55.727902	11	EXECUTED	8:ca924f31bd2a3b219fdcfe78c82dacf4	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.16.1	\N	\N	5174953957
1.4.0	bburke@redhat.com	META-INF/db2-jpa-changelog-1.4.0.xml	2023-09-20 01:55:55.732254	12	MARK_RAN	8:8acad7483e106416bcfa6f3b824a16cd	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.16.1	\N	\N	5174953957
1.5.0	bburke@redhat.com	META-INF/jpa-changelog-1.5.0.xml	2023-09-20 01:55:55.771862	13	EXECUTED	8:9b1266d17f4f87c78226f5055408fd5e	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.16.1	\N	\N	5174953957
1.6.1_from15	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2023-09-20 01:55:55.821557	14	EXECUTED	8:d80ec4ab6dbfe573550ff72396c7e910	addColumn tableName=REALM; addColumn tableName=KEYCLOAK_ROLE; addColumn tableName=CLIENT; createTable tableName=OFFLINE_USER_SESSION; createTable tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_US_SES_PK2, tableName=...		\N	4.16.1	\N	\N	5174953957
1.6.1_from16-pre	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2023-09-20 01:55:55.827128	15	MARK_RAN	8:d86eb172171e7c20b9c849b584d147b2	delete tableName=OFFLINE_CLIENT_SESSION; delete tableName=OFFLINE_USER_SESSION		\N	4.16.1	\N	\N	5174953957
1.6.1_from16	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2023-09-20 01:55:55.831878	16	MARK_RAN	8:5735f46f0fa60689deb0ecdc2a0dea22	dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_US_SES_PK, tableName=OFFLINE_USER_SESSION; dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_CL_SES_PK, tableName=OFFLINE_CLIENT_SESSION; addColumn tableName=OFFLINE_USER_SESSION; update tableName=OF...		\N	4.16.1	\N	\N	5174953957
1.6.1	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2023-09-20 01:55:55.838093	17	EXECUTED	8:d41d8cd98f00b204e9800998ecf8427e	empty		\N	4.16.1	\N	\N	5174953957
1.7.0	bburke@redhat.com	META-INF/jpa-changelog-1.7.0.xml	2023-09-20 01:55:55.912924	18	EXECUTED	8:5c1a8fd2014ac7fc43b90a700f117b23	createTable tableName=KEYCLOAK_GROUP; createTable tableName=GROUP_ROLE_MAPPING; createTable tableName=GROUP_ATTRIBUTE; createTable tableName=USER_GROUP_MEMBERSHIP; createTable tableName=REALM_DEFAULT_GROUPS; addColumn tableName=IDENTITY_PROVIDER; ...		\N	4.16.1	\N	\N	5174953957
1.8.0	mposolda@redhat.com	META-INF/jpa-changelog-1.8.0.xml	2023-09-20 01:55:55.988931	19	EXECUTED	8:1f6c2c2dfc362aff4ed75b3f0ef6b331	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.16.1	\N	\N	5174953957
1.8.0-2	keycloak	META-INF/jpa-changelog-1.8.0.xml	2023-09-20 01:55:55.999876	20	EXECUTED	8:dee9246280915712591f83a127665107	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.16.1	\N	\N	5174953957
1.8.0	mposolda@redhat.com	META-INF/db2-jpa-changelog-1.8.0.xml	2023-09-20 01:55:56.005509	21	MARK_RAN	8:9eb2ee1fa8ad1c5e426421a6f8fdfa6a	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.16.1	\N	\N	5174953957
1.8.0-2	keycloak	META-INF/db2-jpa-changelog-1.8.0.xml	2023-09-20 01:55:56.011236	22	MARK_RAN	8:dee9246280915712591f83a127665107	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.16.1	\N	\N	5174953957
1.9.0	mposolda@redhat.com	META-INF/jpa-changelog-1.9.0.xml	2023-09-20 01:55:56.061393	23	EXECUTED	8:d9fa18ffa355320395b86270680dd4fe	update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=REALM; update tableName=REALM; customChange; dr...		\N	4.16.1	\N	\N	5174953957
1.9.1	keycloak	META-INF/jpa-changelog-1.9.1.xml	2023-09-20 01:55:56.177583	24	EXECUTED	8:90cff506fedb06141ffc1c71c4a1214c	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=PUBLIC_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.16.1	\N	\N	5174953957
1.9.1	keycloak	META-INF/db2-jpa-changelog-1.9.1.xml	2023-09-20 01:55:56.196127	25	MARK_RAN	8:11a788aed4961d6d29c427c063af828c	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.16.1	\N	\N	5174953957
1.9.2	keycloak	META-INF/jpa-changelog-1.9.2.xml	2023-09-20 01:55:56.258151	26	EXECUTED	8:a4218e51e1faf380518cce2af5d39b43	createIndex indexName=IDX_USER_EMAIL, tableName=USER_ENTITY; createIndex indexName=IDX_USER_ROLE_MAPPING, tableName=USER_ROLE_MAPPING; createIndex indexName=IDX_USER_GROUP_MAPPING, tableName=USER_GROUP_MEMBERSHIP; createIndex indexName=IDX_USER_CO...		\N	4.16.1	\N	\N	5174953957
authz-2.0.0	psilva@redhat.com	META-INF/jpa-changelog-authz-2.0.0.xml	2023-09-20 01:55:56.388735	27	EXECUTED	8:d9e9a1bfaa644da9952456050f07bbdc	createTable tableName=RESOURCE_SERVER; addPrimaryKey constraintName=CONSTRAINT_FARS, tableName=RESOURCE_SERVER; addUniqueConstraint constraintName=UK_AU8TT6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER; createTable tableName=RESOURCE_SERVER_RESOU...		\N	4.16.1	\N	\N	5174953957
authz-2.5.1	psilva@redhat.com	META-INF/jpa-changelog-authz-2.5.1.xml	2023-09-20 01:55:56.394631	28	EXECUTED	8:d1bf991a6163c0acbfe664b615314505	update tableName=RESOURCE_SERVER_POLICY		\N	4.16.1	\N	\N	5174953957
2.1.0-KEYCLOAK-5461	bburke@redhat.com	META-INF/jpa-changelog-2.1.0.xml	2023-09-20 01:55:56.523257	29	EXECUTED	8:88a743a1e87ec5e30bf603da68058a8c	createTable tableName=BROKER_LINK; createTable tableName=FED_USER_ATTRIBUTE; createTable tableName=FED_USER_CONSENT; createTable tableName=FED_USER_CONSENT_ROLE; createTable tableName=FED_USER_CONSENT_PROT_MAPPER; createTable tableName=FED_USER_CR...		\N	4.16.1	\N	\N	5174953957
2.2.0	bburke@redhat.com	META-INF/jpa-changelog-2.2.0.xml	2023-09-20 01:55:56.55304	30	EXECUTED	8:c5517863c875d325dea463d00ec26d7a	addColumn tableName=ADMIN_EVENT_ENTITY; createTable tableName=CREDENTIAL_ATTRIBUTE; createTable tableName=FED_CREDENTIAL_ATTRIBUTE; modifyDataType columnName=VALUE, tableName=CREDENTIAL; addForeignKeyConstraint baseTableName=FED_CREDENTIAL_ATTRIBU...		\N	4.16.1	\N	\N	5174953957
2.3.0	bburke@redhat.com	META-INF/jpa-changelog-2.3.0.xml	2023-09-20 01:55:56.595691	31	EXECUTED	8:ada8b4833b74a498f376d7136bc7d327	createTable tableName=FEDERATED_USER; addPrimaryKey constraintName=CONSTR_FEDERATED_USER, tableName=FEDERATED_USER; dropDefaultValue columnName=TOTP, tableName=USER_ENTITY; dropColumn columnName=TOTP, tableName=USER_ENTITY; addColumn tableName=IDE...		\N	4.16.1	\N	\N	5174953957
2.4.0	bburke@redhat.com	META-INF/jpa-changelog-2.4.0.xml	2023-09-20 01:55:56.606291	32	EXECUTED	8:b9b73c8ea7299457f99fcbb825c263ba	customChange		\N	4.16.1	\N	\N	5174953957
2.5.0	bburke@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2023-09-20 01:55:56.619105	33	EXECUTED	8:07724333e625ccfcfc5adc63d57314f3	customChange; modifyDataType columnName=USER_ID, tableName=OFFLINE_USER_SESSION		\N	4.16.1	\N	\N	5174953957
2.5.0-unicode-oracle	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2023-09-20 01:55:56.624816	34	MARK_RAN	8:8b6fd445958882efe55deb26fc541a7b	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.16.1	\N	\N	5174953957
2.5.0-unicode-other-dbs	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2023-09-20 01:55:56.69149	35	EXECUTED	8:29b29cfebfd12600897680147277a9d7	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.16.1	\N	\N	5174953957
2.5.0-duplicate-email-support	slawomir@dabek.name	META-INF/jpa-changelog-2.5.0.xml	2023-09-20 01:55:56.704667	36	EXECUTED	8:73ad77ca8fd0410c7f9f15a471fa52bc	addColumn tableName=REALM		\N	4.16.1	\N	\N	5174953957
2.5.0-unique-group-names	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2023-09-20 01:55:56.726013	37	EXECUTED	8:64f27a6fdcad57f6f9153210f2ec1bdb	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.16.1	\N	\N	5174953957
2.5.1	bburke@redhat.com	META-INF/jpa-changelog-2.5.1.xml	2023-09-20 01:55:56.739095	38	EXECUTED	8:27180251182e6c31846c2ddab4bc5781	addColumn tableName=FED_USER_CONSENT		\N	4.16.1	\N	\N	5174953957
3.0.0	bburke@redhat.com	META-INF/jpa-changelog-3.0.0.xml	2023-09-20 01:55:56.750815	39	EXECUTED	8:d56f201bfcfa7a1413eb3e9bc02978f9	addColumn tableName=IDENTITY_PROVIDER		\N	4.16.1	\N	\N	5174953957
3.2.0-fix	keycloak	META-INF/jpa-changelog-3.2.0.xml	2023-09-20 01:55:56.754982	40	MARK_RAN	8:91f5522bf6afdc2077dfab57fbd3455c	addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS		\N	4.16.1	\N	\N	5174953957
3.2.0-fix-with-keycloak-5416	keycloak	META-INF/jpa-changelog-3.2.0.xml	2023-09-20 01:55:56.765221	41	MARK_RAN	8:0f01b554f256c22caeb7d8aee3a1cdc8	dropIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS; addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS; createIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS		\N	4.16.1	\N	\N	5174953957
3.2.0-fix-offline-sessions	hmlnarik	META-INF/jpa-changelog-3.2.0.xml	2023-09-20 01:55:56.776384	42	EXECUTED	8:ab91cf9cee415867ade0e2df9651a947	customChange		\N	4.16.1	\N	\N	5174953957
3.2.0-fixed	keycloak	META-INF/jpa-changelog-3.2.0.xml	2023-09-20 01:55:57.033621	43	EXECUTED	8:ceac9b1889e97d602caf373eadb0d4b7	addColumn tableName=REALM; dropPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_PK2, tableName=OFFLINE_CLIENT_SESSION; dropColumn columnName=CLIENT_SESSION_ID, tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_P...		\N	4.16.1	\N	\N	5174953957
3.3.0	keycloak	META-INF/jpa-changelog-3.3.0.xml	2023-09-20 01:55:57.045488	44	EXECUTED	8:84b986e628fe8f7fd8fd3c275c5259f2	addColumn tableName=USER_ENTITY		\N	4.16.1	\N	\N	5174953957
authz-3.4.0.CR1-resource-server-pk-change-part1	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2023-09-20 01:55:57.05484	45	EXECUTED	8:a164ae073c56ffdbc98a615493609a52	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_RESOURCE; addColumn tableName=RESOURCE_SERVER_SCOPE		\N	4.16.1	\N	\N	5174953957
authz-3.4.0.CR1-resource-server-pk-change-part2-KEYCLOAK-6095	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2023-09-20 01:55:57.064212	46	EXECUTED	8:70a2b4f1f4bd4dbf487114bdb1810e64	customChange		\N	4.16.1	\N	\N	5174953957
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2023-09-20 01:55:57.068558	47	MARK_RAN	8:7be68b71d2f5b94b8df2e824f2860fa2	dropIndex indexName=IDX_RES_SERV_POL_RES_SERV, tableName=RESOURCE_SERVER_POLICY; dropIndex indexName=IDX_RES_SRV_RES_RES_SRV, tableName=RESOURCE_SERVER_RESOURCE; dropIndex indexName=IDX_RES_SRV_SCOPE_RES_SRV, tableName=RESOURCE_SERVER_SCOPE		\N	4.16.1	\N	\N	5174953957
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed-nodropindex	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2023-09-20 01:55:57.138436	48	EXECUTED	8:bab7c631093c3861d6cf6144cd944982	addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_POLICY; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_RESOURCE; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, ...		\N	4.16.1	\N	\N	5174953957
authn-3.4.0.CR1-refresh-token-max-reuse	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2023-09-20 01:55:57.145241	49	EXECUTED	8:fa809ac11877d74d76fe40869916daad	addColumn tableName=REALM		\N	4.16.1	\N	\N	5174953957
3.4.0	keycloak	META-INF/jpa-changelog-3.4.0.xml	2023-09-20 01:55:57.223773	50	EXECUTED	8:fac23540a40208f5f5e326f6ceb4d291	addPrimaryKey constraintName=CONSTRAINT_REALM_DEFAULT_ROLES, tableName=REALM_DEFAULT_ROLES; addPrimaryKey constraintName=CONSTRAINT_COMPOSITE_ROLE, tableName=COMPOSITE_ROLE; addPrimaryKey constraintName=CONSTR_REALM_DEFAULT_GROUPS, tableName=REALM...		\N	4.16.1	\N	\N	5174953957
3.4.0-KEYCLOAK-5230	hmlnarik@redhat.com	META-INF/jpa-changelog-3.4.0.xml	2023-09-20 01:55:57.284875	51	EXECUTED	8:2612d1b8a97e2b5588c346e817307593	createIndex indexName=IDX_FU_ATTRIBUTE, tableName=FED_USER_ATTRIBUTE; createIndex indexName=IDX_FU_CONSENT, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CONSENT_RU, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CREDENTIAL, t...		\N	4.16.1	\N	\N	5174953957
3.4.1	psilva@redhat.com	META-INF/jpa-changelog-3.4.1.xml	2023-09-20 01:55:57.295313	52	EXECUTED	8:9842f155c5db2206c88bcb5d1046e941	modifyDataType columnName=VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.16.1	\N	\N	5174953957
3.4.2	keycloak	META-INF/jpa-changelog-3.4.2.xml	2023-09-20 01:55:57.300905	53	EXECUTED	8:2e12e06e45498406db72d5b3da5bbc76	update tableName=REALM		\N	4.16.1	\N	\N	5174953957
3.4.2-KEYCLOAK-5172	mkanis@redhat.com	META-INF/jpa-changelog-3.4.2.xml	2023-09-20 01:55:57.3055	54	EXECUTED	8:33560e7c7989250c40da3abdabdc75a4	update tableName=CLIENT		\N	4.16.1	\N	\N	5174953957
4.0.0-KEYCLOAK-6335	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2023-09-20 01:55:57.322038	55	EXECUTED	8:87a8d8542046817a9107c7eb9cbad1cd	createTable tableName=CLIENT_AUTH_FLOW_BINDINGS; addPrimaryKey constraintName=C_CLI_FLOW_BIND, tableName=CLIENT_AUTH_FLOW_BINDINGS		\N	4.16.1	\N	\N	5174953957
4.0.0-CLEANUP-UNUSED-TABLE	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2023-09-20 01:55:57.331346	56	EXECUTED	8:3ea08490a70215ed0088c273d776311e	dropTable tableName=CLIENT_IDENTITY_PROV_MAPPING		\N	4.16.1	\N	\N	5174953957
4.0.0-KEYCLOAK-6228	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2023-09-20 01:55:57.364298	57	EXECUTED	8:2d56697c8723d4592ab608ce14b6ed68	dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; dropNotNullConstraint columnName=CLIENT_ID, tableName=USER_CONSENT; addColumn tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHO...		\N	4.16.1	\N	\N	5174953957
4.0.0-KEYCLOAK-5579-fixed	mposolda@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2023-09-20 01:55:57.509148	58	EXECUTED	8:3e423e249f6068ea2bbe48bf907f9d86	dropForeignKeyConstraint baseTableName=CLIENT_TEMPLATE_ATTRIBUTES, constraintName=FK_CL_TEMPL_ATTR_TEMPL; renameTable newTableName=CLIENT_SCOPE_ATTRIBUTES, oldTableName=CLIENT_TEMPLATE_ATTRIBUTES; renameColumn newColumnName=SCOPE_ID, oldColumnName...		\N	4.16.1	\N	\N	5174953957
authz-4.0.0.CR1	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.CR1.xml	2023-09-20 01:55:57.557737	59	EXECUTED	8:15cabee5e5df0ff099510a0fc03e4103	createTable tableName=RESOURCE_SERVER_PERM_TICKET; addPrimaryKey constraintName=CONSTRAINT_FAPMT, tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRHO213XCX4WNKOG82SSPMT...		\N	4.16.1	\N	\N	5174953957
authz-4.0.0.Beta3	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.Beta3.xml	2023-09-20 01:55:57.568492	60	EXECUTED	8:4b80200af916ac54d2ffbfc47918ab0e	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRPO2128CX4WNKOG82SSRFY, referencedTableName=RESOURCE_SERVER_POLICY		\N	4.16.1	\N	\N	5174953957
authz-4.2.0.Final	mhajas@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2023-09-20 01:55:57.584718	61	EXECUTED	8:66564cd5e168045d52252c5027485bbb	createTable tableName=RESOURCE_URIS; addForeignKeyConstraint baseTableName=RESOURCE_URIS, constraintName=FK_RESOURCE_SERVER_URIS, referencedTableName=RESOURCE_SERVER_RESOURCE; customChange; dropColumn columnName=URI, tableName=RESOURCE_SERVER_RESO...		\N	4.16.1	\N	\N	5174953957
authz-4.2.0.Final-KEYCLOAK-9944	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2023-09-20 01:55:57.597635	62	EXECUTED	8:1c7064fafb030222be2bd16ccf690f6f	addPrimaryKey constraintName=CONSTRAINT_RESOUR_URIS_PK, tableName=RESOURCE_URIS		\N	4.16.1	\N	\N	5174953957
4.2.0-KEYCLOAK-6313	wadahiro@gmail.com	META-INF/jpa-changelog-4.2.0.xml	2023-09-20 01:55:57.607861	63	EXECUTED	8:2de18a0dce10cdda5c7e65c9b719b6e5	addColumn tableName=REQUIRED_ACTION_PROVIDER		\N	4.16.1	\N	\N	5174953957
4.3.0-KEYCLOAK-7984	wadahiro@gmail.com	META-INF/jpa-changelog-4.3.0.xml	2023-09-20 01:55:57.614172	64	EXECUTED	8:03e413dd182dcbd5c57e41c34d0ef682	update tableName=REQUIRED_ACTION_PROVIDER		\N	4.16.1	\N	\N	5174953957
4.6.0-KEYCLOAK-7950	psilva@redhat.com	META-INF/jpa-changelog-4.6.0.xml	2023-09-20 01:55:57.620688	65	EXECUTED	8:d27b42bb2571c18fbe3fe4e4fb7582a7	update tableName=RESOURCE_SERVER_RESOURCE		\N	4.16.1	\N	\N	5174953957
4.6.0-KEYCLOAK-8377	keycloak	META-INF/jpa-changelog-4.6.0.xml	2023-09-20 01:55:57.642073	66	EXECUTED	8:698baf84d9fd0027e9192717c2154fb8	createTable tableName=ROLE_ATTRIBUTE; addPrimaryKey constraintName=CONSTRAINT_ROLE_ATTRIBUTE_PK, tableName=ROLE_ATTRIBUTE; addForeignKeyConstraint baseTableName=ROLE_ATTRIBUTE, constraintName=FK_ROLE_ATTRIBUTE_ID, referencedTableName=KEYCLOAK_ROLE...		\N	4.16.1	\N	\N	5174953957
4.6.0-KEYCLOAK-8555	gideonray@gmail.com	META-INF/jpa-changelog-4.6.0.xml	2023-09-20 01:55:57.656487	67	EXECUTED	8:ced8822edf0f75ef26eb51582f9a821a	createIndex indexName=IDX_COMPONENT_PROVIDER_TYPE, tableName=COMPONENT		\N	4.16.1	\N	\N	5174953957
4.7.0-KEYCLOAK-1267	sguilhen@redhat.com	META-INF/jpa-changelog-4.7.0.xml	2023-09-20 01:55:57.665767	68	EXECUTED	8:f0abba004cf429e8afc43056df06487d	addColumn tableName=REALM		\N	4.16.1	\N	\N	5174953957
4.7.0-KEYCLOAK-7275	keycloak	META-INF/jpa-changelog-4.7.0.xml	2023-09-20 01:55:57.688216	69	EXECUTED	8:6662f8b0b611caa359fcf13bf63b4e24	renameColumn newColumnName=CREATED_ON, oldColumnName=LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION; addNotNullConstraint columnName=CREATED_ON, tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_USER_SESSION; customChange; createIn...		\N	4.16.1	\N	\N	5174953957
4.8.0-KEYCLOAK-8835	sguilhen@redhat.com	META-INF/jpa-changelog-4.8.0.xml	2023-09-20 01:55:57.701746	70	EXECUTED	8:9e6b8009560f684250bdbdf97670d39e	addNotNullConstraint columnName=SSO_MAX_LIFESPAN_REMEMBER_ME, tableName=REALM; addNotNullConstraint columnName=SSO_IDLE_TIMEOUT_REMEMBER_ME, tableName=REALM		\N	4.16.1	\N	\N	5174953957
authz-7.0.0-KEYCLOAK-10443	psilva@redhat.com	META-INF/jpa-changelog-authz-7.0.0.xml	2023-09-20 01:55:57.714523	71	EXECUTED	8:4223f561f3b8dc655846562b57bb502e	addColumn tableName=RESOURCE_SERVER		\N	4.16.1	\N	\N	5174953957
8.0.0-adding-credential-columns	keycloak	META-INF/jpa-changelog-8.0.0.xml	2023-09-20 01:55:57.731277	72	EXECUTED	8:215a31c398b363ce383a2b301202f29e	addColumn tableName=CREDENTIAL; addColumn tableName=FED_USER_CREDENTIAL		\N	4.16.1	\N	\N	5174953957
8.0.0-updating-credential-data-not-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2023-09-20 01:55:57.741352	73	EXECUTED	8:83f7a671792ca98b3cbd3a1a34862d3d	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.16.1	\N	\N	5174953957
8.0.0-updating-credential-data-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2023-09-20 01:55:57.745149	74	MARK_RAN	8:f58ad148698cf30707a6efbdf8061aa7	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.16.1	\N	\N	5174953957
8.0.0-credential-cleanup-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2023-09-20 01:55:57.770174	75	EXECUTED	8:79e4fd6c6442980e58d52ffc3ee7b19c	dropDefaultValue columnName=COUNTER, tableName=CREDENTIAL; dropDefaultValue columnName=DIGITS, tableName=CREDENTIAL; dropDefaultValue columnName=PERIOD, tableName=CREDENTIAL; dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; dropColumn ...		\N	4.16.1	\N	\N	5174953957
8.0.0-resource-tag-support	keycloak	META-INF/jpa-changelog-8.0.0.xml	2023-09-20 01:55:57.7844	76	EXECUTED	8:87af6a1e6d241ca4b15801d1f86a297d	addColumn tableName=MIGRATION_MODEL; createIndex indexName=IDX_UPDATE_TIME, tableName=MIGRATION_MODEL		\N	4.16.1	\N	\N	5174953957
9.0.0-always-display-client	keycloak	META-INF/jpa-changelog-9.0.0.xml	2023-09-20 01:55:57.802243	77	EXECUTED	8:b44f8d9b7b6ea455305a6d72a200ed15	addColumn tableName=CLIENT		\N	4.16.1	\N	\N	5174953957
9.0.0-drop-constraints-for-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2023-09-20 01:55:57.807786	78	MARK_RAN	8:2d8ed5aaaeffd0cb004c046b4a903ac5	dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5PMT, tableName=RESOURCE_SERVER_PERM_TICKET; dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER_RESOURCE; dropPrimaryKey constraintName=CONSTRAINT_O...		\N	4.16.1	\N	\N	5174953957
9.0.0-increase-column-size-federated-fk	keycloak	META-INF/jpa-changelog-9.0.0.xml	2023-09-20 01:55:57.853295	79	EXECUTED	8:e290c01fcbc275326c511633f6e2acde	modifyDataType columnName=CLIENT_ID, tableName=FED_USER_CONSENT; modifyDataType columnName=CLIENT_REALM_CONSTRAINT, tableName=KEYCLOAK_ROLE; modifyDataType columnName=OWNER, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=CLIENT_ID, ta...		\N	4.16.1	\N	\N	5174953957
9.0.0-recreate-constraints-after-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2023-09-20 01:55:57.858266	80	MARK_RAN	8:c9db8784c33cea210872ac2d805439f8	addNotNullConstraint columnName=CLIENT_ID, tableName=OFFLINE_CLIENT_SESSION; addNotNullConstraint columnName=OWNER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNullConstraint columnName=REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNull...		\N	4.16.1	\N	\N	5174953957
9.0.1-add-index-to-client.client_id	keycloak	META-INF/jpa-changelog-9.0.1.xml	2023-09-20 01:55:57.876585	81	EXECUTED	8:95b676ce8fc546a1fcfb4c92fae4add5	createIndex indexName=IDX_CLIENT_ID, tableName=CLIENT		\N	4.16.1	\N	\N	5174953957
9.0.1-KEYCLOAK-12579-drop-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2023-09-20 01:55:57.880085	82	MARK_RAN	8:38a6b2a41f5651018b1aca93a41401e5	dropUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.16.1	\N	\N	5174953957
9.0.1-KEYCLOAK-12579-add-not-null-constraint	keycloak	META-INF/jpa-changelog-9.0.1.xml	2023-09-20 01:55:57.888729	83	EXECUTED	8:3fb99bcad86a0229783123ac52f7609c	addNotNullConstraint columnName=PARENT_GROUP, tableName=KEYCLOAK_GROUP		\N	4.16.1	\N	\N	5174953957
9.0.1-KEYCLOAK-12579-recreate-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2023-09-20 01:55:57.89354	84	MARK_RAN	8:64f27a6fdcad57f6f9153210f2ec1bdb	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.16.1	\N	\N	5174953957
9.0.1-add-index-to-events	keycloak	META-INF/jpa-changelog-9.0.1.xml	2023-09-20 01:55:57.909497	85	EXECUTED	8:ab4f863f39adafd4c862f7ec01890abc	createIndex indexName=IDX_EVENT_TIME, tableName=EVENT_ENTITY		\N	4.16.1	\N	\N	5174953957
map-remove-ri	keycloak	META-INF/jpa-changelog-11.0.0.xml	2023-09-20 01:55:57.921767	86	EXECUTED	8:13c419a0eb336e91ee3a3bf8fda6e2a7	dropForeignKeyConstraint baseTableName=REALM, constraintName=FK_TRAF444KK6QRKMS7N56AIWQ5Y; dropForeignKeyConstraint baseTableName=KEYCLOAK_ROLE, constraintName=FK_KJHO5LE2C0RAL09FL8CM9WFW9		\N	4.16.1	\N	\N	5174953957
map-remove-ri	keycloak	META-INF/jpa-changelog-12.0.0.xml	2023-09-20 01:55:57.934816	87	EXECUTED	8:e3fb1e698e0471487f51af1ed80fe3ac	dropForeignKeyConstraint baseTableName=REALM_DEFAULT_GROUPS, constraintName=FK_DEF_GROUPS_GROUP; dropForeignKeyConstraint baseTableName=REALM_DEFAULT_ROLES, constraintName=FK_H4WPD7W4HSOOLNI3H0SW7BTJE; dropForeignKeyConstraint baseTableName=CLIENT...		\N	4.16.1	\N	\N	5174953957
12.1.0-add-realm-localization-table	keycloak	META-INF/jpa-changelog-12.0.0.xml	2023-09-20 01:55:57.952939	88	EXECUTED	8:babadb686aab7b56562817e60bf0abd0	createTable tableName=REALM_LOCALIZATIONS; addPrimaryKey tableName=REALM_LOCALIZATIONS		\N	4.16.1	\N	\N	5174953957
default-roles	keycloak	META-INF/jpa-changelog-13.0.0.xml	2023-09-20 01:55:57.96543	89	EXECUTED	8:72d03345fda8e2f17093d08801947773	addColumn tableName=REALM; customChange		\N	4.16.1	\N	\N	5174953957
default-roles-cleanup	keycloak	META-INF/jpa-changelog-13.0.0.xml	2023-09-20 01:55:57.979753	90	EXECUTED	8:61c9233951bd96ffecd9ba75f7d978a4	dropTable tableName=REALM_DEFAULT_ROLES; dropTable tableName=CLIENT_DEFAULT_ROLES		\N	4.16.1	\N	\N	5174953957
13.0.0-KEYCLOAK-16844	keycloak	META-INF/jpa-changelog-13.0.0.xml	2023-09-20 01:55:57.993187	91	EXECUTED	8:ea82e6ad945cec250af6372767b25525	createIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.16.1	\N	\N	5174953957
map-remove-ri-13.0.0	keycloak	META-INF/jpa-changelog-13.0.0.xml	2023-09-20 01:55:58.005359	92	EXECUTED	8:d3f4a33f41d960ddacd7e2ef30d126b3	dropForeignKeyConstraint baseTableName=DEFAULT_CLIENT_SCOPE, constraintName=FK_R_DEF_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SCOPE_CLIENT, constraintName=FK_C_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SC...		\N	4.16.1	\N	\N	5174953957
13.0.0-KEYCLOAK-17992-drop-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2023-09-20 01:55:58.010019	93	MARK_RAN	8:1284a27fbd049d65831cb6fc07c8a783	dropPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CLSCOPE_CL, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CL_CLSCOPE, tableName=CLIENT_SCOPE_CLIENT		\N	4.16.1	\N	\N	5174953957
13.0.0-increase-column-size-federated	keycloak	META-INF/jpa-changelog-13.0.0.xml	2023-09-20 01:55:58.028897	94	EXECUTED	8:9d11b619db2ae27c25853b8a37cd0dea	modifyDataType columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; modifyDataType columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT		\N	4.16.1	\N	\N	5174953957
13.0.0-KEYCLOAK-17992-recreate-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2023-09-20 01:55:58.033706	95	MARK_RAN	8:3002bb3997451bb9e8bac5c5cd8d6327	addNotNullConstraint columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; addNotNullConstraint columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT; addPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; createIndex indexName=...		\N	4.16.1	\N	\N	5174953957
json-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-13.0.0.xml	2023-09-20 01:55:58.043201	96	EXECUTED	8:dfbee0d6237a23ef4ccbb7a4e063c163	addColumn tableName=REALM_ATTRIBUTE; update tableName=REALM_ATTRIBUTE; dropColumn columnName=VALUE, tableName=REALM_ATTRIBUTE; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=REALM_ATTRIBUTE		\N	4.16.1	\N	\N	5174953957
14.0.0-KEYCLOAK-11019	keycloak	META-INF/jpa-changelog-14.0.0.xml	2023-09-20 01:55:58.067149	97	EXECUTED	8:75f3e372df18d38c62734eebb986b960	createIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USER, tableName=OFFLINE_USER_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.16.1	\N	\N	5174953957
14.0.0-KEYCLOAK-18286	keycloak	META-INF/jpa-changelog-14.0.0.xml	2023-09-20 01:55:58.071485	98	MARK_RAN	8:7fee73eddf84a6035691512c85637eef	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.16.1	\N	\N	5174953957
14.0.0-KEYCLOAK-18286-revert	keycloak	META-INF/jpa-changelog-14.0.0.xml	2023-09-20 01:55:58.086215	99	MARK_RAN	8:7a11134ab12820f999fbf3bb13c3adc8	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.16.1	\N	\N	5174953957
14.0.0-KEYCLOAK-18286-supported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2023-09-20 01:55:58.103388	100	EXECUTED	8:c0f6eaac1f3be773ffe54cb5b8482b70	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.16.1	\N	\N	5174953957
14.0.0-KEYCLOAK-18286-unsupported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2023-09-20 01:55:58.111083	101	MARK_RAN	8:18186f0008b86e0f0f49b0c4d0e842ac	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.16.1	\N	\N	5174953957
KEYCLOAK-17267-add-index-to-user-attributes	keycloak	META-INF/jpa-changelog-14.0.0.xml	2023-09-20 01:55:58.132518	102	EXECUTED	8:09c2780bcb23b310a7019d217dc7b433	createIndex indexName=IDX_USER_ATTRIBUTE_NAME, tableName=USER_ATTRIBUTE		\N	4.16.1	\N	\N	5174953957
KEYCLOAK-18146-add-saml-art-binding-identifier	keycloak	META-INF/jpa-changelog-14.0.0.xml	2023-09-20 01:55:58.141821	103	EXECUTED	8:276a44955eab693c970a42880197fff2	customChange		\N	4.16.1	\N	\N	5174953957
15.0.0-KEYCLOAK-18467	keycloak	META-INF/jpa-changelog-15.0.0.xml	2023-09-20 01:55:58.154566	104	EXECUTED	8:ba8ee3b694d043f2bfc1a1079d0760d7	addColumn tableName=REALM_LOCALIZATIONS; update tableName=REALM_LOCALIZATIONS; dropColumn columnName=TEXTS, tableName=REALM_LOCALIZATIONS; renameColumn newColumnName=TEXTS, oldColumnName=TEXTS_NEW, tableName=REALM_LOCALIZATIONS; addNotNullConstrai...		\N	4.16.1	\N	\N	5174953957
17.0.0-9562	keycloak	META-INF/jpa-changelog-17.0.0.xml	2023-09-20 01:55:58.181669	105	EXECUTED	8:5e06b1d75f5d17685485e610c2851b17	createIndex indexName=IDX_USER_SERVICE_ACCOUNT, tableName=USER_ENTITY		\N	4.16.1	\N	\N	5174953957
18.0.0-10625-IDX_ADMIN_EVENT_TIME	keycloak	META-INF/jpa-changelog-18.0.0.xml	2023-09-20 01:55:58.196417	106	EXECUTED	8:4b80546c1dc550ac552ee7b24a4ab7c0	createIndex indexName=IDX_ADMIN_EVENT_TIME, tableName=ADMIN_EVENT_ENTITY		\N	4.16.1	\N	\N	5174953957
19.0.0-10135	keycloak	META-INF/jpa-changelog-19.0.0.xml	2023-09-20 01:55:58.20696	107	EXECUTED	8:af510cd1bb2ab6339c45372f3e491696	customChange		\N	4.16.1	\N	\N	5174953957
20.0.0-12964-supported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2023-09-20 01:55:58.219963	108	EXECUTED	8:05c99fc610845ef66ee812b7921af0ef	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.16.1	\N	\N	5174953957
20.0.0-12964-unsupported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2023-09-20 01:55:58.224443	109	MARK_RAN	8:314e803baf2f1ec315b3464e398b8247	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.16.1	\N	\N	5174953957
client-attributes-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-20.0.0.xml	2023-09-20 01:55:58.237156	110	EXECUTED	8:56e4677e7e12556f70b604c573840100	addColumn tableName=CLIENT_ATTRIBUTES; update tableName=CLIENT_ATTRIBUTES; dropColumn columnName=VALUE, tableName=CLIENT_ATTRIBUTES; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=CLIENT_ATTRIBUTES		\N	4.16.1	\N	\N	5174953957
21.0.2-17277	keycloak	META-INF/jpa-changelog-21.0.2.xml	2023-09-20 01:55:58.245491	111	EXECUTED	8:8806cb33d2a546ce770384bf98cf6eac	customChange		\N	4.16.1	\N	\N	5174953957
21.1.0-19404	keycloak	META-INF/jpa-changelog-21.1.0.xml	2023-09-20 01:55:58.30011	112	EXECUTED	8:fdb2924649d30555ab3a1744faba4928	modifyDataType columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=LOGIC, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=POLICY_ENFORCE_MODE, tableName=RESOURCE_SERVER		\N	4.16.1	\N	\N	5174953957
21.1.0-19404-2	keycloak	META-INF/jpa-changelog-21.1.0.xml	2023-09-20 01:55:58.303303	113	MARK_RAN	8:1c96cc2b10903bd07a03670098d67fd6	addColumn tableName=RESOURCE_SERVER_POLICY; update tableName=RESOURCE_SERVER_POLICY; dropColumn columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; renameColumn newColumnName=DECISION_STRATEGY, oldColumnName=DECISION_STRATEGY_NEW, tabl...		\N	4.16.1	\N	\N	5174953957
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
1000	f	\N	\N
1001	f	\N	\N
\.


--
-- Data for Name: default_client_scope; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.default_client_scope (realm_id, scope_id, default_scope) FROM stdin;
fc3e583c-0722-4ee1-aeef-70af67cf78ba	9675f3c3-801f-4102-8165-5a2b91c820e8	f
fc3e583c-0722-4ee1-aeef-70af67cf78ba	127baadd-fd95-4586-8c9a-774c0ad2e6a7	t
fc3e583c-0722-4ee1-aeef-70af67cf78ba	3953f190-5b31-4665-af77-301a3b04cb29	t
fc3e583c-0722-4ee1-aeef-70af67cf78ba	7199870d-2829-4c8b-8e69-f3b3719078c8	t
fc3e583c-0722-4ee1-aeef-70af67cf78ba	2bd1932f-4a4c-470e-93c3-8c63f14cbd7d	f
fc3e583c-0722-4ee1-aeef-70af67cf78ba	8571a084-e752-4bc0-826e-5c3101b2806d	f
fc3e583c-0722-4ee1-aeef-70af67cf78ba	410cc8bb-cebd-4584-b751-c7338c65ddfd	t
fc3e583c-0722-4ee1-aeef-70af67cf78ba	4bed6796-2ccc-484c-aedd-3c6d10fb1be9	t
fc3e583c-0722-4ee1-aeef-70af67cf78ba	e79f8057-e166-476f-aef4-3acb08abc6c2	f
fc3e583c-0722-4ee1-aeef-70af67cf78ba	052aba50-63e4-4959-85f3-5bcbd50aab37	t
89f9614c-27fd-44d6-b07c-c527669d1d5c	37e9482e-66ca-4472-b441-a8241d28f09e	f
89f9614c-27fd-44d6-b07c-c527669d1d5c	98920d25-d442-4201-8c78-76726c58ca8e	t
89f9614c-27fd-44d6-b07c-c527669d1d5c	35bd4d78-1490-42bc-8588-4b6b48231f94	t
89f9614c-27fd-44d6-b07c-c527669d1d5c	e8cbe350-c045-4f95-a4af-17fd5672cbc3	t
89f9614c-27fd-44d6-b07c-c527669d1d5c	fee9a005-61ba-4f89-8be1-f66d3aa9d73b	f
89f9614c-27fd-44d6-b07c-c527669d1d5c	0ab80d9d-976f-45fb-9531-5bbb53abdc47	f
89f9614c-27fd-44d6-b07c-c527669d1d5c	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c	t
89f9614c-27fd-44d6-b07c-c527669d1d5c	0595dccb-d437-44a9-92b7-2458928021ef	t
89f9614c-27fd-44d6-b07c-c527669d1d5c	3c40af86-1182-49d5-ba42-50eed305b394	f
89f9614c-27fd-44d6-b07c-c527669d1d5c	3db49690-a543-488c-b811-1eb95b78a43d	t
\.


--
-- Data for Name: event_entity; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.event_entity (id, client_id, details_json, error, ip_address, realm_id, session_id, event_time, type, user_id) FROM stdin;
610e214e-ecf2-4426-b075-3eff611a2aaa	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695645008004	LOGIN_ERROR	\N
ac393c3e-322d-4c60-85c3-08c15beff701	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695645008389	LOGIN_ERROR	\N
ec0f4100-7d95-4310-b2ab-b318d04a4f02	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695645010289	LOGIN_ERROR	\N
361d7674-6ffb-4645-b379-bd5d6fbfc5de	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695645010735	LOGIN_ERROR	\N
244db135-b4e0-4a42-a60b-f936ed493a83	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695645011791	LOGIN_ERROR	\N
63112407-6bde-4a35-90e6-78c90e61fda9	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695645012126	LOGIN_ERROR	\N
10232a98-7151-4c61-95e0-d324d1e7b08e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695645014503	LOGIN_ERROR	\N
ecc0b8d3-5e22-44da-9369-e02e326c3340	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695645014944	LOGIN_ERROR	\N
9173d2a6-43ee-45e1-b139-76acf0cad172	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646074188	LOGIN_ERROR	\N
8a9efeb1-6d7f-477f-b7e9-3307ec24bbba	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646074505	LOGIN_ERROR	\N
2c12fcc7-f898-493a-97dd-a1cf90482e0e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646075501	LOGIN_ERROR	\N
4f292ba6-c866-4068-aef1-337cc88ade00	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646075840	LOGIN_ERROR	\N
2414822f-70a2-407b-9ab6-6b57f65afb22	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646076658	LOGIN_ERROR	\N
da167c53-efde-4ba3-8584-30743231d81e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646076977	LOGIN_ERROR	\N
5ac75edb-13bf-4a69-9bfc-7fbda7f93c61	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646078292	LOGIN_ERROR	\N
21ffbccb-686b-4342-bd74-e4c468ad34a4	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646078601	LOGIN_ERROR	\N
ceec5e43-0976-4f14-bb02-3516e71912ae	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646203585	LOGIN_ERROR	\N
9b9733c3-7b03-4f74-a995-dd32b1b5c00e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646203907	LOGIN_ERROR	\N
cebadf11-5113-4036-a9f4-063ebdd1d234	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646205136	LOGIN_ERROR	\N
cba84717-eb90-4e3d-ba43-8ead6fa633e4	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646205531	LOGIN_ERROR	\N
8cc6c61e-4ab7-4821-ba9c-a384407d1db9	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646206397	LOGIN_ERROR	\N
7c428a0a-8e31-45a2-8df3-b7ec5afc2cbc	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646206672	LOGIN_ERROR	\N
15f42843-4e41-4d34-ae19-dd1450437d88	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646208018	LOGIN_ERROR	\N
3f009f92-b6a2-410b-b63e-97a13729d38a	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646208341	LOGIN_ERROR	\N
89f80102-5d67-4172-a995-3141b0bbdd1a	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646223751	LOGIN_ERROR	\N
f6749b72-1abd-4658-821b-976fc1f7ab94	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646224081	LOGIN_ERROR	\N
8c9275ea-dca7-477a-bdbc-2d095426f336	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646225134	LOGIN_ERROR	\N
549c2dd9-96f4-4f4a-80bf-7d03c5c9ef21	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646225509	LOGIN_ERROR	\N
10e428e5-d5c3-4160-81a3-fe941dce8cad	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646226430	LOGIN_ERROR	\N
f52bc8ef-0a53-4a21-9605-43a17bc0976c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646226753	LOGIN_ERROR	\N
ff0f4555-c285-4919-b4f6-c2199fbf8056	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646228095	LOGIN_ERROR	\N
d48734e4-7eee-4dfd-82ca-3fc8ec56fce5	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646228415	LOGIN_ERROR	\N
06fac73e-a9ee-48ae-ad29-2bb71a967377	client-id-backend-1	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695646228559	LOGIN_ERROR	\N
11da045b-d856-459c-bab0-a50bec64c612	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646266955	LOGIN_ERROR	\N
9ff96bbe-8cf9-4e38-886c-5fb7080cd684	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646268820	LOGIN_ERROR	\N
0bcbacb3-e488-4b91-a95a-f625cf4036f0	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646270509	LOGIN_ERROR	\N
2e960a5d-3a26-4203-a9e6-64b50ca63a78	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646272053	LOGIN_ERROR	\N
12dfb549-c7bb-46aa-adee-0889340c7851	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646290845	LOGIN_ERROR	\N
f1af0be0-1016-4d8d-9562-1ad502e44222	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646291880	LOGIN_ERROR	\N
27898a6d-cd2f-4ff3-bbbc-a4ee7232df43	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646293530	LOGIN_ERROR	\N
4f1aa944-bae9-4874-a871-bc3a9d41e270	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695646294934	LOGIN_ERROR	\N
621de0da-57b6-42c5-aa5d-f5f293c7dadf	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647019596	LOGIN_ERROR	\N
980c05f4-08cc-4a60-9503-3eab95c81c2b	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647020683	LOGIN_ERROR	\N
91985359-bb17-47d3-ab50-b1a949adeb06	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647022323	LOGIN_ERROR	\N
02323ddf-5a42-486d-8652-0783db99c51b	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647023746	LOGIN_ERROR	\N
7e359f95-73ff-4b4b-9833-936892b25873	master-realm	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647024370	LOGIN_ERROR	\N
bd56e191-4636-4983-bc1c-3cc7e73aa9f9	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647034698	LOGIN_ERROR	\N
c050a239-d655-48da-9e37-a25f14abf8f4	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647035824	LOGIN_ERROR	\N
def9224e-1e44-4bd7-9d96-905a3e4a6623	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647037358	LOGIN_ERROR	\N
46efa356-b762-46dc-a3a1-35812264ffa6	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647038713	LOGIN_ERROR	\N
895f7d69-a74e-42fe-ab61-9e2c608c1649	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647147737	LOGIN_ERROR	\N
b93e4636-8bd0-4c07-8d31-0e179ee3892b	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647149161	LOGIN_ERROR	\N
7b5e592e-1671-4e7a-8aa8-7d87368d0f56	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647150783	LOGIN_ERROR	\N
769275c9-111d-4f77-af31-66a770b6bb4c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647152416	LOGIN_ERROR	\N
72072c19-8b4f-42ae-83e8-b8bd8a10045c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647153952	LOGIN_ERROR	\N
28888e78-ca5b-405d-8eb3-3bbfe53746e2	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647250227	LOGIN_ERROR	\N
27062294-510b-4e01-968d-42957d1b657e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647252173	LOGIN_ERROR	\N
b5716ffb-8a1b-4dad-9431-ab20041d4f05	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647254592	LOGIN_ERROR	\N
15b9913e-1d33-4504-baa3-0360255b6427	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647257093	LOGIN_ERROR	\N
1dbc7f5d-b3cd-4371-9cef-6921c4bf907e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647258922	LOGIN_ERROR	\N
abfb6b41-81f8-4a01-a2c7-7dc7cfabe530	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647362616	LOGIN_ERROR	\N
59db3577-db89-4bfc-ab70-d18bcd7d7b88	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647364171	LOGIN_ERROR	\N
ff1abf55-d643-45b0-9e24-eef9d742644d	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647366043	LOGIN_ERROR	\N
27688c0d-d5eb-48ce-9ffd-7bd5cfd2b418	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647367951	LOGIN_ERROR	\N
7748af10-139e-4c77-ae3f-5d9d28756d16	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647369798	LOGIN_ERROR	\N
184e1e42-43d5-40fe-a59a-6352a576c066	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647570173	LOGIN_ERROR	\N
4cfbe7d1-c594-4978-8e9b-8e807dbe651b	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647571875	LOGIN_ERROR	\N
20bbc27b-5274-464c-8ddd-34725eea002c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647575268	LOGIN_ERROR	\N
c1bbbea4-f2d4-4ac9-8aa1-be346d777bf8	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647577222	LOGIN_ERROR	\N
09acf604-a38b-4e96-b455-d2f3234477f4	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647579411	LOGIN_ERROR	\N
7072c816-44c3-487d-8972-d87fc8ac7167	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647597133	LOGIN_ERROR	\N
d527bf1e-8200-499f-a609-b8487726adf6	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647598821	LOGIN_ERROR	\N
142da46b-9dcd-4ada-9c5d-4adee90c566b	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647600910	LOGIN_ERROR	\N
72db77e9-d7e5-4e73-91bd-78da7f5c1bd6	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647603010	LOGIN_ERROR	\N
39ad8927-775b-4861-a60e-92bcb2c563c2	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647605068	LOGIN_ERROR	\N
cacd00cd-e061-4882-a6fd-3c73dfd08a5e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647616943	LOGIN_ERROR	\N
98c191ea-3073-41e1-b0d3-9dcb25fab0a2	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647618574	LOGIN_ERROR	\N
dbfa48a5-d7b4-47de-9d60-08e221d6f4f8	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647621144	LOGIN_ERROR	\N
569dbf54-cc61-4360-a6c5-913fbc4fb7f0	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647624309	LOGIN_ERROR	\N
6d159b76-da17-47db-bb32-fa159c7c43cd	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647627233	LOGIN_ERROR	\N
46ff19da-b5e1-49c3-8bde-fbb172afe7ed	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647855598	LOGIN_ERROR	\N
3b3dab98-6fcd-4303-8408-24fccc0f446c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647857262	LOGIN_ERROR	\N
5a7a362e-644b-4368-b000-8d244cb0e53e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647860202	LOGIN_ERROR	\N
01046425-d0c1-4f78-8bdc-1cb9d80e666f	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647862198	LOGIN_ERROR	\N
b0777162-44e1-4c64-aab5-701df722a984	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695647864100	LOGIN_ERROR	\N
904be7ba-7e49-43cd-a458-607a6d1f422d	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648093970	LOGIN_ERROR	\N
c57380a0-280d-46bc-a47c-f082cf2ac7bc	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648095456	LOGIN_ERROR	\N
14f2bca3-c0cb-4a9c-9c57-28114440b81c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648097442	LOGIN_ERROR	\N
f07f4dfd-04d7-44aa-84d8-4921dff5b3a4	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648099309	LOGIN_ERROR	\N
0a45c1b4-e337-45ee-9f73-40ce91b8c0be	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648101641	LOGIN_ERROR	\N
77ec1942-fe30-42c1-96cc-04c71b20350c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648111299	LOGIN_ERROR	\N
553e151c-9e9a-4704-9712-0fa130019518	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648112878	LOGIN_ERROR	\N
65ed314c-8ede-442b-b79a-d2d4129fadfd	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648115086	LOGIN_ERROR	\N
e45c6917-daa9-48c9-adac-cbfd5326ff64	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648116954	LOGIN_ERROR	\N
0b79617d-a6ec-459b-abee-3d2dc10a1183	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648118730	LOGIN_ERROR	\N
7e2d164e-7dbc-4b17-892a-540c3808a57c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648854729	LOGIN_ERROR	\N
5fd9d8d3-ce31-492f-ba59-8a617f7c0279	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648856597	LOGIN_ERROR	\N
17729aff-087d-40b1-81be-4add9da651ae	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648858955	LOGIN_ERROR	\N
4fe76725-8d9b-4186-83f0-ab45add9b69a	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648861298	LOGIN_ERROR	\N
45130e07-a157-4169-a994-03c583dc6bd2	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648863331	LOGIN_ERROR	\N
7e71bb12-fc1f-4e89-8071-bcf290730fc1	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648919681	LOGIN_ERROR	\N
3a61a1e8-54ed-4294-b41e-6af887030407	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648921397	LOGIN_ERROR	\N
e1b81ebb-94ec-4d22-b1b2-c306249cc6b1	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648923689	LOGIN_ERROR	\N
6d2d5711-b8ac-420e-958b-929fac758ca6	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648925719	LOGIN_ERROR	\N
f14d6537-2cc2-4f91-81cf-e1235f5e53b1	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648927779	LOGIN_ERROR	\N
bb84a0ae-0a8a-4346-83c5-faf58df526ac	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648946152	LOGIN_ERROR	\N
a576b10c-4efe-4a7f-b0f0-1d176cc16f35	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648947806	LOGIN_ERROR	\N
95812d0a-5e86-4d73-87c0-da5c2657f1cd	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648949891	LOGIN_ERROR	\N
bc77bf76-bfea-479f-a3a9-72842fedfeef	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648952056	LOGIN_ERROR	\N
6dc25f42-5cf4-49b9-85ac-e57d52272310	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695648954022	LOGIN_ERROR	\N
d0e14a8c-7780-435c-9651-c8c173060b7d	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649268475	LOGIN_ERROR	\N
102e7881-c2d2-4f45-a282-86619301c368	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649270802	LOGIN_ERROR	\N
8456d31d-21e2-4d85-a2d8-95bb3b3c5692	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649273028	LOGIN_ERROR	\N
29b39d09-e646-4f92-ab03-389871ded2a4	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649275271	LOGIN_ERROR	\N
19bcd01a-d610-46a0-89cb-9c50b395cf67	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649277583	LOGIN_ERROR	\N
dd3efdc3-023c-47b0-a1c0-7fb15ca89c4c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649581546	LOGIN_ERROR	\N
e9360cf8-ae33-499e-a172-411f416c08bb	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649583627	LOGIN_ERROR	\N
214a6b98-3c78-4edd-9480-8b5318ef2476	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649587712	LOGIN_ERROR	\N
182a86c0-1214-4f1b-8137-e070e9ba5a3e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649591700	LOGIN_ERROR	\N
5d9b09b0-ba8e-48de-a950-a6a6435af0d5	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649594914	LOGIN_ERROR	\N
ecf2c6a3-9813-45ad-9eb5-2a2217c31241	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649701988	LOGIN_ERROR	\N
1a0e0165-4790-4bf6-90c7-a4f0b0fe9b68	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649704089	LOGIN_ERROR	\N
d09b26e9-bfab-4d0f-8230-1a2f4e368890	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649706701	LOGIN_ERROR	\N
ead148e3-da93-4a68-b215-4b9af724252c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649710354	LOGIN_ERROR	\N
3b4da431-7ee8-4882-b8de-0c1d19dd14a3	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649710788	LOGIN_ERROR	\N
491df4e2-118c-473b-a210-40b72bcf039a	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649713614	LOGIN_ERROR	\N
bf431680-57fa-4300-93bd-2633d08210e4	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649714758	LOGIN_ERROR	\N
60380351-c8a3-43b9-9906-52990948a0cd	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649717051	LOGIN_ERROR	\N
c425a1e1-7741-4a78-86bf-1bd4422d478d	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649718813	LOGIN_ERROR	\N
df0d1981-c230-493e-a1f4-0cf048675918	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649720563	LOGIN_ERROR	\N
0ed6b40c-476b-4962-9f43-3904df9c2e1a	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649762143	LOGIN_ERROR	\N
d158761f-895c-4be2-be3f-a08389b11590	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649764207	LOGIN_ERROR	\N
ace955bb-ae33-4f19-87e0-cc0d6ec8cb72	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649766585	LOGIN_ERROR	\N
377085d0-430b-4cd1-84af-6a304c27593d	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649768744	LOGIN_ERROR	\N
b04458e0-937f-4e3a-8503-1247681707ca	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649770747	LOGIN_ERROR	\N
66100277-a764-4b8f-b1db-084623af3453	master-realm	{"grant_type":"password"}	invalid_client_credentials	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649771333	LOGIN_ERROR	\N
724158ef-8d01-46d0-8108-7564b31e764f	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695649999316	LOGIN_ERROR	\N
dccd2bcc-7838-486b-a587-f4681d4f81bd	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650000931	LOGIN_ERROR	\N
9031edc8-b553-47db-aee9-515c9e98ccad	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650003304	LOGIN_ERROR	\N
e9aea9fd-4e5f-475e-bca8-fe5c410a77d7	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650005337	LOGIN_ERROR	\N
ff7a6cf5-14b0-40a8-8836-8863d551df67	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650007267	LOGIN_ERROR	\N
ffe29e87-0501-4d1d-a319-62a36db477fb	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650092658	LOGIN_ERROR	\N
8c1b3914-baef-4e39-9cac-3d7b068f416b	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650094486	LOGIN_ERROR	\N
dcebfeb9-1b9e-483f-a2c2-b60ff4e34a04	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650096697	LOGIN_ERROR	\N
abd35eac-35b1-4135-9c23-c1a7abd319e9	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650098846	LOGIN_ERROR	\N
947a5d78-f0a5-413e-a847-a1e82d8f4369	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650100765	LOGIN_ERROR	\N
ef6e0b7a-57f7-43df-9faa-026238bd24d7	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650155776	LOGIN_ERROR	\N
87ca87a1-06fd-417a-bd4f-62d2279c6dff	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650157550	LOGIN_ERROR	\N
be5bce6e-2c0d-4b50-a4cd-933d916ec083	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650159597	LOGIN_ERROR	\N
35b20a1b-2367-4002-8270-3ab274d33808	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650161694	LOGIN_ERROR	\N
3415b7f1-76b7-4f91-be5e-9ce08b91bd43	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650163597	LOGIN_ERROR	\N
50e108c2-4d0c-499b-93e0-320ac1acf7c8	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650237985	LOGIN_ERROR	\N
aa4d4ebd-e418-4ced-a515-bffdfbac10fe	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650239762	LOGIN_ERROR	\N
d635098e-f0e2-4b82-958a-d19a2e369490	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650242264	LOGIN_ERROR	\N
ab899e6d-db64-4f7a-a12e-61a51d186b32	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650243994	LOGIN_ERROR	\N
fca99708-72b2-4a01-b991-92c007071e13	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650245594	LOGIN_ERROR	\N
6a4325bc-c66f-4c8b-8400-752126f32dd5	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650253657	LOGIN_ERROR	\N
61bf6d69-1e37-43ca-87f0-a6190dd11ff9	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650255377	LOGIN_ERROR	\N
6c5c8406-409a-4f6f-a5da-fe20d729f1d7	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650257431	LOGIN_ERROR	\N
a3222094-9939-47cf-9a8b-3ff7cdf42e23	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650259308	LOGIN_ERROR	\N
0a40aba2-72ad-48cd-aa5e-9239d4663cd8	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650261305	LOGIN_ERROR	\N
acc4cc26-e432-4547-b9df-1b26c340cb91	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650312905	LOGIN_ERROR	\N
77fd6906-1ad7-49be-a91d-b62b7ffb1191	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650314551	LOGIN_ERROR	\N
4cf1484f-9fdc-4b60-a6c9-1b847afefe6a	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650316802	LOGIN_ERROR	\N
67a3266c-13e3-4094-b53e-04ab59ddfe03	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650319041	LOGIN_ERROR	\N
917413ee-afbf-4c7e-a6f5-819910b69b37	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650321026	LOGIN_ERROR	\N
269ccf32-3a76-4b29-bfd1-0e6dca53797c	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650708077	LOGIN_ERROR	\N
b9bf2a41-8f68-4806-a56c-e60483d163ac	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650710105	LOGIN_ERROR	\N
859e83d7-1597-4ea6-86e8-88f3208ae282	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650712159	LOGIN_ERROR	\N
e9c6b9a7-24a0-42a4-8761-d392c3e3e1c9	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650714252	LOGIN_ERROR	\N
e4ac9c8d-b60c-44e8-a6e9-a9d34139a805	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650716179	LOGIN_ERROR	\N
eeb7b6f9-31fd-4849-b60c-363cf78baf21	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650813926	LOGIN_ERROR	\N
ea3f1932-7dc7-478b-87f2-a722bb64021b	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650815459	LOGIN_ERROR	\N
22d8e8a1-ab8f-494b-8fc0-2db9f0b84755	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650817507	LOGIN_ERROR	\N
f0acdd43-0bae-4012-bc32-05cecabef47b	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650819641	LOGIN_ERROR	\N
90e1e315-bfcc-44ef-a867-3276dc7f5b0d	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650821657	LOGIN_ERROR	\N
ac3f482f-388d-44d9-b360-ee2ee9a2e0e4	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650910800	LOGIN_ERROR	\N
e7c27601-bf52-4498-892b-420bb49a185e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650912561	LOGIN_ERROR	\N
faa4bbeb-f934-4946-888a-643297d668b8	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650914718	LOGIN_ERROR	\N
a0e9f01e-be09-4221-b018-b22a6cb0decb	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650916855	LOGIN_ERROR	\N
a2c46eab-5cf0-4ae5-a9d0-db6dba0653f0	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650920883	LOGIN_ERROR	\N
0d1e83a3-6c3a-4c0c-9971-bfb78742f6f0	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650941567	LOGIN_ERROR	\N
b2041266-ce93-4390-bc21-3b952495d3d7	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650943297	LOGIN_ERROR	\N
12d2a677-2225-42c3-ab29-9540e7e23c0d	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650945730	LOGIN_ERROR	\N
2f47de8c-335e-4d32-9d0f-11c1b226558f	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650948919	LOGIN_ERROR	\N
4a0b33c4-d3ef-4a18-a8e2-d92ee0b10e9a	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695650951246	LOGIN_ERROR	\N
06466220-db79-450e-9401-3baf5be17101	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651009466	LOGIN_ERROR	\N
c82be43e-dd2c-4e15-905d-de744bd3a9c8	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651011075	LOGIN_ERROR	\N
eba117b6-1c6b-4514-ba46-83f6d963b4a8	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651013299	LOGIN_ERROR	\N
236bdfdf-3cc4-4bdf-b179-da23169c2981	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651015284	LOGIN_ERROR	\N
fe42b961-0120-41bc-a2dd-d414b5164887	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651017174	LOGIN_ERROR	\N
c68e3ae6-d100-4024-bd04-9a5172a82bdb	account-console	{"auth_method":"openid-connect","auth_type":"code","redirect_uri":"http://localhost:9090/realms/realm-pantanal-dev/account/#/personal-info","code_id":"68b4f179-6442-47ea-9124-c2d1041dfb6d","username":"admin"}	user_not_found	172.20.0.1	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	1695651134100	LOGIN_ERROR	\N
c28e0aa4-9335-4e74-8078-ed31792e26bd	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651367491	LOGIN_ERROR	\N
5e4038bb-e2d8-45d4-9588-e66f1cd83916	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651369240	LOGIN_ERROR	\N
74ac3806-62b0-4bbd-94ba-097197364256	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651371386	LOGIN_ERROR	\N
96919416-df81-41e4-bde1-ad2e04704e31	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651373378	LOGIN_ERROR	\N
3cecd502-9e29-47c2-84a6-48ad7898faa6	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651375405	LOGIN_ERROR	\N
12438719-c9c8-44a1-b73e-f8771b5a31de	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651462717	LOGIN_ERROR	\N
09af8c64-e507-418e-9024-bbdfd5528132	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651464376	LOGIN_ERROR	\N
7be0a96d-cfde-4d48-b2df-2c45a9fdfebf	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651466334	LOGIN_ERROR	\N
60847d30-46e8-4580-b31c-dc820bf546be	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651468312	LOGIN_ERROR	\N
e663edbc-88ec-4156-b48d-d5b17381b497	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651470282	LOGIN_ERROR	\N
89136352-76dc-40ca-9076-cfebb07688ef	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651521577	LOGIN_ERROR	\N
98c0d4c8-484a-47bc-b026-595203a58cff	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651523483	LOGIN_ERROR	\N
f24bbbb1-4348-4798-9ad8-6b34128c1c80	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651525799	LOGIN_ERROR	\N
771dc00a-835c-4811-8df8-9cc140e1d2a0	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651528046	LOGIN_ERROR	\N
f5243e86-6f98-4518-9fb3-1985a0375403	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651530580	LOGIN_ERROR	\N
c7b81af5-97f0-4ba1-a19b-3c19a99c2a0d	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651534747	LOGIN_ERROR	\N
1c4a83c9-ce6c-42c1-8884-c1b8fe5fa5c7	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651536743	LOGIN_ERROR	\N
e85ef38f-6d5e-4937-871b-261e2abdcff1	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651538933	LOGIN_ERROR	\N
cdb4ed1a-bdd4-483d-9949-e6e4daaec4ec	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651540998	LOGIN_ERROR	\N
555e7d11-1de2-4c55-991e-7d41ece326bb	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651542690	LOGIN_ERROR	\N
b92649d3-b914-4295-91b2-17fc19c26170	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651552137	LOGIN_ERROR	\N
03954947-e45a-4138-9575-f5db39c114b7	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651553689	LOGIN_ERROR	\N
6258f258-ae54-46f3-b6b0-58b90a9c482d	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651555706	LOGIN_ERROR	\N
caeb2147-9d06-4b02-8cc4-6e178d48929a	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651557777	LOGIN_ERROR	\N
37a701d4-3c88-4835-a785-d22b59ab9014	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651559710	LOGIN_ERROR	\N
2c251ceb-c5b5-4e5c-8706-eaa55be36469	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651749921	LOGIN_ERROR	\N
f6568f63-d1cc-4f51-88d6-abcb7906dc8e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651751626	LOGIN_ERROR	\N
cee3beab-cff9-4468-ad67-5cedfaa9a1ea	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651753657	LOGIN_ERROR	\N
9c34b129-aedd-4b4d-ae7f-6c78038fc67e	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651756153	LOGIN_ERROR	\N
8c3e1e27-2f63-469a-9efe-90b30dac3858	client-id-pantanal	{"grant_type":"password"}	client_not_found	172.20.0.1	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	1695651758871	LOGIN_ERROR	\N
\.


--
-- Data for Name: fed_user_attribute; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.fed_user_attribute (id, name, user_id, realm_id, storage_provider_id, value) FROM stdin;
\.


--
-- Data for Name: fed_user_consent; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.fed_user_consent (id, client_id, user_id, realm_id, storage_provider_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: fed_user_consent_cl_scope; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.fed_user_consent_cl_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: fed_user_credential; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.fed_user_credential (id, salt, type, created_date, user_id, realm_id, storage_provider_id, user_label, secret_data, credential_data, priority) FROM stdin;
\.


--
-- Data for Name: fed_user_group_membership; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.fed_user_group_membership (group_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_required_action; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.fed_user_required_action (required_action, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_role_mapping; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.fed_user_role_mapping (role_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: federated_identity; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.federated_identity (identity_provider, realm_id, federated_user_id, federated_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: federated_user; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.federated_user (id, storage_provider_id, realm_id) FROM stdin;
\.


--
-- Data for Name: group_attribute; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.group_attribute (id, name, value, group_id) FROM stdin;
\.


--
-- Data for Name: group_role_mapping; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.group_role_mapping (role_id, group_id) FROM stdin;
\.


--
-- Data for Name: identity_provider; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.identity_provider (internal_id, enabled, provider_alias, provider_id, store_token, authenticate_by_default, realm_id, add_token_role, trust_email, first_broker_login_flow_id, post_broker_login_flow_id, provider_display_name, link_only) FROM stdin;
\.


--
-- Data for Name: identity_provider_config; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.identity_provider_config (identity_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: identity_provider_mapper; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.identity_provider_mapper (id, name, idp_alias, idp_mapper_name, realm_id) FROM stdin;
\.


--
-- Data for Name: idp_mapper_config; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.idp_mapper_config (idp_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: keycloak_group; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.keycloak_group (id, name, parent_group, realm_id) FROM stdin;
\.


--
-- Data for Name: keycloak_role; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.keycloak_role (id, client_realm_constraint, client_role, description, name, realm_id, client, realm) FROM stdin;
8d754a55-ec91-4fbb-90a4-1061f7104e34	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f	${role_default-roles}	default-roles-master	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	\N
33f6f7d5-d60b-416f-98e8-80737b6fc8a4	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f	${role_create-realm}	create-realm	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	\N
be6d98db-db6b-4646-bafe-eb60bfc00070	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_create-client}	create-client	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
de30f2ce-1830-4785-af30-d484e6996d5a	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_view-realm}	view-realm	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
3dd5882b-3d46-4129-a767-b7a196fd6989	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_view-users}	view-users	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
0d139e77-3c35-4cb2-a4e2-e9df0b678bd4	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_view-clients}	view-clients	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
fc5ba1b3-e7ca-4594-a603-79d03278d997	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_view-events}	view-events	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
56a8ea35-23d4-4749-9381-5696c491b2f4	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_view-identity-providers}	view-identity-providers	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
86142502-b239-4001-a719-49d3e7175cf7	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_view-authorization}	view-authorization	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
12e73d16-7b35-4715-a7ba-ffc00dc8c187	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_manage-realm}	manage-realm	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
d6e50895-e9db-4650-9c89-89fec312dec5	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_manage-users}	manage-users	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
66fd55bf-201b-45f0-bd88-d809a5426ea3	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_manage-clients}	manage-clients	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
df34f42f-bd2e-423b-a202-31e5f23d4f3f	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_manage-events}	manage-events	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
b8fb4a21-0096-494b-93a3-69ca367ed56d	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_manage-identity-providers}	manage-identity-providers	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
c1f8be64-a602-4412-9969-3948a472074b	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_manage-authorization}	manage-authorization	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
903f5c86-3413-426e-8a1e-44bc4f185233	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_query-users}	query-users	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
5463c41b-cc98-431a-9a3d-7eafdc230337	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_query-clients}	query-clients	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
675bdb12-e403-4939-8494-1a9581d1b223	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_query-realms}	query-realms	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f	${role_admin}	admin	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	\N
936e0555-c2bb-4f15-9df4-a91872575200	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_query-groups}	query-groups	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
beef3bb2-8000-4992-b0f6-86faafa3d39e	7626857f-0274-4b0c-90bc-996f940fc6d9	t	${role_view-profile}	view-profile	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7626857f-0274-4b0c-90bc-996f940fc6d9	\N
76fa9fe2-9cd4-4539-b375-b3ac1e3d85b5	7626857f-0274-4b0c-90bc-996f940fc6d9	t	${role_manage-account}	manage-account	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7626857f-0274-4b0c-90bc-996f940fc6d9	\N
846bf181-36f6-4539-a08c-eb154c715705	7626857f-0274-4b0c-90bc-996f940fc6d9	t	${role_manage-account-links}	manage-account-links	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7626857f-0274-4b0c-90bc-996f940fc6d9	\N
c7a374bb-ea7b-4c52-8165-ad05696b4cf5	7626857f-0274-4b0c-90bc-996f940fc6d9	t	${role_view-applications}	view-applications	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7626857f-0274-4b0c-90bc-996f940fc6d9	\N
0f5d5a78-42d1-4c71-bdc3-2e7600213981	7626857f-0274-4b0c-90bc-996f940fc6d9	t	${role_view-consent}	view-consent	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7626857f-0274-4b0c-90bc-996f940fc6d9	\N
e67a0fd8-77d5-449f-ad58-41b4af1454f1	7626857f-0274-4b0c-90bc-996f940fc6d9	t	${role_manage-consent}	manage-consent	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7626857f-0274-4b0c-90bc-996f940fc6d9	\N
54d34dc7-47a9-451f-8a50-095fe4621401	7626857f-0274-4b0c-90bc-996f940fc6d9	t	${role_view-groups}	view-groups	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7626857f-0274-4b0c-90bc-996f940fc6d9	\N
e5795db7-b700-41a4-b2ff-46e6badf962d	7626857f-0274-4b0c-90bc-996f940fc6d9	t	${role_delete-account}	delete-account	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7626857f-0274-4b0c-90bc-996f940fc6d9	\N
c6f4d5b4-1809-45ae-bc16-e45d09214556	e212016a-a377-4661-8acc-6ec56608a8dd	t	${role_read-token}	read-token	fc3e583c-0722-4ee1-aeef-70af67cf78ba	e212016a-a377-4661-8acc-6ec56608a8dd	\N
761ee8eb-e856-422e-a92e-aab073d92268	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	t	${role_impersonation}	impersonation	fc3e583c-0722-4ee1-aeef-70af67cf78ba	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	\N
5690ce3d-1ea7-4f4d-8cc1-2777667c0aa3	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f	${role_offline-access}	offline_access	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	\N
a274a639-6011-41a1-b593-3088aea52eb0	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f	${role_uma_authorization}	uma_authorization	fc3e583c-0722-4ee1-aeef-70af67cf78ba	\N	\N
cc85fef9-25b9-4515-98ce-0be4a5df89ac	89f9614c-27fd-44d6-b07c-c527669d1d5c	f	${role_default-roles}	default-roles-realm-pantanal-dev	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	\N
84a57036-c660-46e9-8269-e65339589114	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_create-client}	create-client	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
2c7dfd6f-8b67-417e-9ca3-2f2515133738	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_view-realm}	view-realm	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
4bac61f4-8b7d-4d79-923e-caa7c51b2390	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_view-users}	view-users	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
f35f20c6-1468-4f81-bc57-6336369c1f2a	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_view-clients}	view-clients	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
f66ef8c0-440e-4110-9475-e6bf6921288a	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_view-events}	view-events	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
323d8c47-6191-4afc-89f7-ed4da4f34171	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_view-identity-providers}	view-identity-providers	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
0ea30235-713a-492f-8e55-3e786e3703aa	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_view-authorization}	view-authorization	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
72e05d0a-f850-4c8b-a6f9-7fa865fa8c12	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_manage-realm}	manage-realm	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
185454c3-3bc4-4589-b1e9-f0817dfd7878	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_manage-users}	manage-users	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
4c38fb49-9048-44de-b22b-ba5195f737a2	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_manage-clients}	manage-clients	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
99539257-f641-4395-97c0-1fce95793597	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_manage-events}	manage-events	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
f9b9699d-be45-4f29-9a10-c937ceba33e2	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_manage-identity-providers}	manage-identity-providers	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
664ecbb7-bfcf-492f-9200-311da3360358	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_manage-authorization}	manage-authorization	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
6e7e357f-90a9-488d-8496-73d8799800d7	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_query-users}	query-users	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
d797e279-0d71-46a9-8a9a-03aca51cd633	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_query-clients}	query-clients	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
a27ff082-2d2b-4130-8e9a-bdd8eea1c286	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_query-realms}	query-realms	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
fcfa2fc5-8e27-4f43-9884-dc750fcba5fd	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_query-groups}	query-groups	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
4b0f62a5-156c-4327-9984-5381cd7aa440	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_realm-admin}	realm-admin	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
df625d4b-3286-42d2-9ae5-531b78dadd4f	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_create-client}	create-client	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
cd5b9411-3733-4d7e-9348-127592db0beb	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_view-realm}	view-realm	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
836205f2-2323-46e9-ab27-121cb3593371	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_view-users}	view-users	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
e003249d-98e8-4e0c-b11a-6161e53a2a07	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_view-clients}	view-clients	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
6af6f895-1117-42f5-89d0-f823fdca15d4	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_view-events}	view-events	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
e51f0b1e-547e-4817-bd74-335b274fd0c1	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_view-identity-providers}	view-identity-providers	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
f11ee328-716b-429f-94f3-a885e06bb1b1	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_view-authorization}	view-authorization	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
a8fdd752-e5e5-4c0c-a909-344192edc59d	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_manage-realm}	manage-realm	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
fb640fe1-20fa-47d0-95fe-c773b55ab9ef	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_manage-users}	manage-users	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
529e1198-678b-428b-864c-e4dc79c6b20a	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_manage-clients}	manage-clients	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
13f279f5-94d5-4511-91e8-855870cd4882	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_manage-events}	manage-events	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
83d5f6e8-816f-4051-b65e-71bd11fa28fa	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_manage-identity-providers}	manage-identity-providers	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
0ab41a31-20a4-4e4a-a77d-e6e44f3b0122	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_manage-authorization}	manage-authorization	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
8ea07074-4203-4e0c-b716-241b4475de7f	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_query-users}	query-users	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
b99a6f9d-ab79-4dce-9b33-a414af25dafc	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_query-clients}	query-clients	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
abdf3ba6-ca04-4c81-ae3f-d252d345e24b	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_query-realms}	query-realms	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
174b809c-a0bd-4420-a65e-106b7192df47	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_query-groups}	query-groups	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
ea28a649-4409-405f-a833-47b993b90197	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	t	${role_view-profile}	view-profile	89f9614c-27fd-44d6-b07c-c527669d1d5c	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	\N
cb51f8b0-0f6f-401e-8bc8-eb247e2b6fed	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	t	${role_manage-account}	manage-account	89f9614c-27fd-44d6-b07c-c527669d1d5c	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	\N
0edbf0a2-3eec-4654-97e5-7f1860c7e2d1	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	t	${role_manage-account-links}	manage-account-links	89f9614c-27fd-44d6-b07c-c527669d1d5c	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	\N
e6f38089-98db-43cd-97cb-ca647734bee0	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	t	${role_view-applications}	view-applications	89f9614c-27fd-44d6-b07c-c527669d1d5c	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	\N
cd5f6c22-798c-4711-b35b-ebea1cddd37e	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	t	${role_view-consent}	view-consent	89f9614c-27fd-44d6-b07c-c527669d1d5c	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	\N
62689410-a0e3-47de-b733-64783de4a697	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	t	${role_manage-consent}	manage-consent	89f9614c-27fd-44d6-b07c-c527669d1d5c	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	\N
e9add83b-98b2-4060-aecd-7e80521f91b4	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	t	${role_view-groups}	view-groups	89f9614c-27fd-44d6-b07c-c527669d1d5c	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	\N
c0c9eae3-28a9-4588-ad41-2166e88a1bb9	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	t	${role_delete-account}	delete-account	89f9614c-27fd-44d6-b07c-c527669d1d5c	d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	\N
885156fc-c918-4cee-936f-7a103591e8e4	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	t	${role_impersonation}	impersonation	fc3e583c-0722-4ee1-aeef-70af67cf78ba	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	\N
4a490a09-61ac-4c3a-9119-045fe3bf0a67	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	t	${role_impersonation}	impersonation	89f9614c-27fd-44d6-b07c-c527669d1d5c	38b8ccc7-9e4e-4e34-ba43-0f89077f79ca	\N
b2974e49-f098-4d80-a7e4-0c307a7fcc15	78c7cc85-99eb-4a64-903b-f0730fdf1383	t	${role_read-token}	read-token	89f9614c-27fd-44d6-b07c-c527669d1d5c	78c7cc85-99eb-4a64-903b-f0730fdf1383	\N
8eb3be08-fd48-4e0b-8a58-7488f1a8f69c	89f9614c-27fd-44d6-b07c-c527669d1d5c	f	${role_offline-access}	offline_access	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	\N
9c98d677-c8f8-418e-8929-3093bba6d755	89f9614c-27fd-44d6-b07c-c527669d1d5c	f	${role_uma_authorization}	uma_authorization	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	\N
85c6dc54-2fc5-4c52-b82c-1d17b61328f0	89f9614c-27fd-44d6-b07c-c527669d1d5c	f		SOCIAL_ACTION	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	\N
b13c9ab4-140d-4195-906d-d599a431a6f8	89f9614c-27fd-44d6-b07c-c527669d1d5c	f		SOCIAL_ACTION_CREATE	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	\N
057baeb4-3a85-4816-9551-c809c7f96b51	89f9614c-27fd-44d6-b07c-c527669d1d5c	f		SOCIAL_ACTION_GET_ALL	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	\N
c94a3122-79b2-42a2-8190-a8070f79ea51	89f9614c-27fd-44d6-b07c-c527669d1d5c	f		SOCIAL_ACTION_GET_ONE	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	\N
e1f65982-48b3-48a7-94de-bc487a1d39e0	89f9614c-27fd-44d6-b07c-c527669d1d5c	f		SOCIAL_ACTION_UPDATE	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	\N
19554cab-896e-4183-8096-e541a4f5d051	89f9614c-27fd-44d6-b07c-c527669d1d5c	f		SOCIAL_ACTION_DELETE	89f9614c-27fd-44d6-b07c-c527669d1d5c	\N	\N
\.


--
-- Data for Name: migration_model; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.migration_model (id, version, update_time) FROM stdin;
p31hk	21.1.1	1695174958
\.


--
-- Data for Name: offline_client_session; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.offline_client_session (user_session_id, client_id, offline_flag, "timestamp", data, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: offline_user_session; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.offline_user_session (user_session_id, user_id, realm_id, created_on, offline_flag, data, last_session_refresh) FROM stdin;
\.


--
-- Data for Name: policy_config; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.policy_config (policy_id, name, value) FROM stdin;
\.


--
-- Data for Name: protocol_mapper; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.protocol_mapper (id, name, protocol, protocol_mapper_name, client_id, client_scope_id) FROM stdin;
8f2eb941-fab8-47f4-899e-86b80f124d2d	audience resolve	openid-connect	oidc-audience-resolve-mapper	8f880efc-b681-4dd1-bc3f-a71b772ef16b	\N
bd3faf2b-7121-40d0-a72e-5215d578e07b	locale	openid-connect	oidc-usermodel-attribute-mapper	194b8b1c-ff39-4947-bac4-a35411a838fd	\N
606ab1cc-3f9e-4dd8-af6a-5e81d9ab26fd	role list	saml	saml-role-list-mapper	\N	127baadd-fd95-4586-8c9a-774c0ad2e6a7
78f68ebf-ea19-4a04-9a38-12c4531465f6	full name	openid-connect	oidc-full-name-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
7b3bc654-887f-436f-86bf-57430190c16d	family name	openid-connect	oidc-usermodel-property-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
d4ccf547-6c21-4358-aa81-00fe4cc47729	given name	openid-connect	oidc-usermodel-property-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
96f00334-04f8-43e0-9339-27f4fea0027f	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
2eefbd5c-0c7c-48db-badf-011deaaa0b75	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
fea87866-cf4e-4548-823c-6ffa1f6847ab	username	openid-connect	oidc-usermodel-property-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
698fb9c8-df2b-489e-9083-6851ed013506	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
2cf9afb2-702c-472c-8a7c-a11477c6ce7a	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
3aa203fc-bcbc-429c-ad4b-b0158dd9ec41	website	openid-connect	oidc-usermodel-attribute-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
6de25a46-b410-41da-8826-9fdad205fed8	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
2bfd3902-ee46-4d56-89b8-7c28d6192f71	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
6cc6c3b3-d203-42f2-be0e-0f07243cf776	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
58b1a3c2-2a79-4d50-9266-5629fc275a89	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
169a4147-2215-491c-a2cf-fd6c78f21160	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	3953f190-5b31-4665-af77-301a3b04cb29
390d9bb6-492f-41ca-890b-3c740c1a0a50	email	openid-connect	oidc-usermodel-property-mapper	\N	7199870d-2829-4c8b-8e69-f3b3719078c8
7be446a8-2348-45f2-87d3-5a652e4506b9	email verified	openid-connect	oidc-usermodel-property-mapper	\N	7199870d-2829-4c8b-8e69-f3b3719078c8
ac2b8c8b-8eb8-4653-840f-bc76ad785452	address	openid-connect	oidc-address-mapper	\N	2bd1932f-4a4c-470e-93c3-8c63f14cbd7d
484ac752-14a5-4e81-a43f-fee964c7d63b	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	8571a084-e752-4bc0-826e-5c3101b2806d
fb588171-037f-43fe-a05e-3682eb4da714	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	8571a084-e752-4bc0-826e-5c3101b2806d
bde49369-7aa3-427b-9e14-7e6930033849	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	410cc8bb-cebd-4584-b751-c7338c65ddfd
63bddc57-793b-4ffe-bf12-e7c80ff9567a	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	410cc8bb-cebd-4584-b751-c7338c65ddfd
f8f05985-5e47-4e11-bf43-48e4cc178c14	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	410cc8bb-cebd-4584-b751-c7338c65ddfd
ae6d4367-6d13-40d3-a9b9-6c63349f7620	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	4bed6796-2ccc-484c-aedd-3c6d10fb1be9
a4bdf80c-3c41-436f-b8b2-1fa19df1708b	upn	openid-connect	oidc-usermodel-property-mapper	\N	e79f8057-e166-476f-aef4-3acb08abc6c2
0aba6eac-129b-4269-a3d5-a17523a4a677	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	e79f8057-e166-476f-aef4-3acb08abc6c2
7ce6e81d-9182-4136-b074-1b81fc9dbc22	acr loa level	openid-connect	oidc-acr-mapper	\N	052aba50-63e4-4959-85f3-5bcbd50aab37
005b0ff0-fec8-40cb-b7fd-4aa4bf881dd5	audience resolve	openid-connect	oidc-audience-resolve-mapper	c559f39f-2212-4360-83ed-4829e52c3e24	\N
904c1945-7e8c-4712-b249-45dd70282134	role list	saml	saml-role-list-mapper	\N	98920d25-d442-4201-8c78-76726c58ca8e
cd1d2cb4-9fb9-4933-a460-7a95f05c2104	full name	openid-connect	oidc-full-name-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
5032e521-ac0f-4b0d-b466-3ea6801f33b2	family name	openid-connect	oidc-usermodel-property-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
505ada5c-02db-48d6-9aef-704c3f3a30fe	given name	openid-connect	oidc-usermodel-property-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
be3b3c0c-f06f-46ac-a288-8437b83178e9	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
d79a37ee-10df-41b3-a70d-2572ddab66f4	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
03ec46ec-8816-428e-a718-658ba897b5db	username	openid-connect	oidc-usermodel-property-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
cce1e042-63d9-4e06-95bd-e689103ebd15	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
67a616a9-742d-42d5-8e5f-0612aa502715	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
f2e4729f-4f0d-4178-8a62-5414ecfef515	website	openid-connect	oidc-usermodel-attribute-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
66213037-0272-4d85-a26f-20d4441dfa09	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
fcef2e0e-48b2-4570-8daa-b0268b8e16a0	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
4783fc4e-e7d2-49c8-8074-380154c41c96	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
9ef9f827-36b8-47c0-abd5-4464d9946abb	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
0f38f27c-15ce-49a2-bec3-fb944c8cfcfb	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	35bd4d78-1490-42bc-8588-4b6b48231f94
d0b1be6e-0c28-4f01-a47d-accb8e141bd9	email	openid-connect	oidc-usermodel-property-mapper	\N	e8cbe350-c045-4f95-a4af-17fd5672cbc3
45260cbd-0a29-438e-a0e0-a5ae2d34042d	email verified	openid-connect	oidc-usermodel-property-mapper	\N	e8cbe350-c045-4f95-a4af-17fd5672cbc3
9d37906a-0670-46b2-9e1a-1622800b6b8a	address	openid-connect	oidc-address-mapper	\N	fee9a005-61ba-4f89-8be1-f66d3aa9d73b
a44864c2-dabd-414d-846f-a772eed22e77	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	0ab80d9d-976f-45fb-9531-5bbb53abdc47
67a79519-e830-4b88-a2a4-76d53e951f55	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	0ab80d9d-976f-45fb-9531-5bbb53abdc47
c6417804-4f6e-4481-b42a-86a0619ea2b3	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c
31a02ffb-1795-4af5-ac7f-861a4744d97b	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c
13ebe420-871d-477d-a752-4745829a2293	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	1330a5c4-ba06-4b7a-82a2-7b3f9bc9ad3c
0d74cf08-6229-43da-a00c-af0fffd0dbd3	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	0595dccb-d437-44a9-92b7-2458928021ef
aa682483-bab1-44e3-b86a-a385f9a823bb	upn	openid-connect	oidc-usermodel-property-mapper	\N	3c40af86-1182-49d5-ba42-50eed305b394
5e17d77b-fd29-4d72-a244-a54aca9fa87f	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	3c40af86-1182-49d5-ba42-50eed305b394
ee4a45b2-83b2-474c-9d21-c10f83c7e08d	acr loa level	openid-connect	oidc-acr-mapper	\N	3db49690-a543-488c-b811-1eb95b78a43d
cea3aeab-5662-49ad-ac36-a1dcfee67276	locale	openid-connect	oidc-usermodel-attribute-mapper	1b41c01a-a904-41a8-9c76-88a152c41ac5	\N
9ef36950-4d5a-446a-8329-8ab9a9c2d776	Client ID	openid-connect	oidc-usersessionmodel-note-mapper	ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	\N
dc2ded80-844e-4507-be7c-d874ff925271	Client Host	openid-connect	oidc-usersessionmodel-note-mapper	ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	\N
4222907d-7ea1-4910-a533-a15f752ace7c	Client IP Address	openid-connect	oidc-usersessionmodel-note-mapper	ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	\N
\.


--
-- Data for Name: protocol_mapper_config; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.protocol_mapper_config (protocol_mapper_id, value, name) FROM stdin;
bd3faf2b-7121-40d0-a72e-5215d578e07b	true	userinfo.token.claim
bd3faf2b-7121-40d0-a72e-5215d578e07b	locale	user.attribute
bd3faf2b-7121-40d0-a72e-5215d578e07b	true	id.token.claim
bd3faf2b-7121-40d0-a72e-5215d578e07b	true	access.token.claim
bd3faf2b-7121-40d0-a72e-5215d578e07b	locale	claim.name
bd3faf2b-7121-40d0-a72e-5215d578e07b	String	jsonType.label
606ab1cc-3f9e-4dd8-af6a-5e81d9ab26fd	false	single
606ab1cc-3f9e-4dd8-af6a-5e81d9ab26fd	Basic	attribute.nameformat
606ab1cc-3f9e-4dd8-af6a-5e81d9ab26fd	Role	attribute.name
169a4147-2215-491c-a2cf-fd6c78f21160	true	userinfo.token.claim
169a4147-2215-491c-a2cf-fd6c78f21160	updatedAt	user.attribute
169a4147-2215-491c-a2cf-fd6c78f21160	true	id.token.claim
169a4147-2215-491c-a2cf-fd6c78f21160	true	access.token.claim
169a4147-2215-491c-a2cf-fd6c78f21160	updated_at	claim.name
169a4147-2215-491c-a2cf-fd6c78f21160	long	jsonType.label
2bfd3902-ee46-4d56-89b8-7c28d6192f71	true	userinfo.token.claim
2bfd3902-ee46-4d56-89b8-7c28d6192f71	birthdate	user.attribute
2bfd3902-ee46-4d56-89b8-7c28d6192f71	true	id.token.claim
2bfd3902-ee46-4d56-89b8-7c28d6192f71	true	access.token.claim
2bfd3902-ee46-4d56-89b8-7c28d6192f71	birthdate	claim.name
2bfd3902-ee46-4d56-89b8-7c28d6192f71	String	jsonType.label
2cf9afb2-702c-472c-8a7c-a11477c6ce7a	true	userinfo.token.claim
2cf9afb2-702c-472c-8a7c-a11477c6ce7a	picture	user.attribute
2cf9afb2-702c-472c-8a7c-a11477c6ce7a	true	id.token.claim
2cf9afb2-702c-472c-8a7c-a11477c6ce7a	true	access.token.claim
2cf9afb2-702c-472c-8a7c-a11477c6ce7a	picture	claim.name
2cf9afb2-702c-472c-8a7c-a11477c6ce7a	String	jsonType.label
2eefbd5c-0c7c-48db-badf-011deaaa0b75	true	userinfo.token.claim
2eefbd5c-0c7c-48db-badf-011deaaa0b75	nickname	user.attribute
2eefbd5c-0c7c-48db-badf-011deaaa0b75	true	id.token.claim
2eefbd5c-0c7c-48db-badf-011deaaa0b75	true	access.token.claim
2eefbd5c-0c7c-48db-badf-011deaaa0b75	nickname	claim.name
2eefbd5c-0c7c-48db-badf-011deaaa0b75	String	jsonType.label
3aa203fc-bcbc-429c-ad4b-b0158dd9ec41	true	userinfo.token.claim
3aa203fc-bcbc-429c-ad4b-b0158dd9ec41	website	user.attribute
3aa203fc-bcbc-429c-ad4b-b0158dd9ec41	true	id.token.claim
3aa203fc-bcbc-429c-ad4b-b0158dd9ec41	true	access.token.claim
3aa203fc-bcbc-429c-ad4b-b0158dd9ec41	website	claim.name
3aa203fc-bcbc-429c-ad4b-b0158dd9ec41	String	jsonType.label
58b1a3c2-2a79-4d50-9266-5629fc275a89	true	userinfo.token.claim
58b1a3c2-2a79-4d50-9266-5629fc275a89	locale	user.attribute
58b1a3c2-2a79-4d50-9266-5629fc275a89	true	id.token.claim
58b1a3c2-2a79-4d50-9266-5629fc275a89	true	access.token.claim
58b1a3c2-2a79-4d50-9266-5629fc275a89	locale	claim.name
58b1a3c2-2a79-4d50-9266-5629fc275a89	String	jsonType.label
698fb9c8-df2b-489e-9083-6851ed013506	true	userinfo.token.claim
698fb9c8-df2b-489e-9083-6851ed013506	profile	user.attribute
698fb9c8-df2b-489e-9083-6851ed013506	true	id.token.claim
698fb9c8-df2b-489e-9083-6851ed013506	true	access.token.claim
698fb9c8-df2b-489e-9083-6851ed013506	profile	claim.name
698fb9c8-df2b-489e-9083-6851ed013506	String	jsonType.label
6cc6c3b3-d203-42f2-be0e-0f07243cf776	true	userinfo.token.claim
6cc6c3b3-d203-42f2-be0e-0f07243cf776	zoneinfo	user.attribute
6cc6c3b3-d203-42f2-be0e-0f07243cf776	true	id.token.claim
6cc6c3b3-d203-42f2-be0e-0f07243cf776	true	access.token.claim
6cc6c3b3-d203-42f2-be0e-0f07243cf776	zoneinfo	claim.name
6cc6c3b3-d203-42f2-be0e-0f07243cf776	String	jsonType.label
6de25a46-b410-41da-8826-9fdad205fed8	true	userinfo.token.claim
6de25a46-b410-41da-8826-9fdad205fed8	gender	user.attribute
6de25a46-b410-41da-8826-9fdad205fed8	true	id.token.claim
6de25a46-b410-41da-8826-9fdad205fed8	true	access.token.claim
6de25a46-b410-41da-8826-9fdad205fed8	gender	claim.name
6de25a46-b410-41da-8826-9fdad205fed8	String	jsonType.label
78f68ebf-ea19-4a04-9a38-12c4531465f6	true	userinfo.token.claim
78f68ebf-ea19-4a04-9a38-12c4531465f6	true	id.token.claim
78f68ebf-ea19-4a04-9a38-12c4531465f6	true	access.token.claim
7b3bc654-887f-436f-86bf-57430190c16d	true	userinfo.token.claim
7b3bc654-887f-436f-86bf-57430190c16d	lastName	user.attribute
7b3bc654-887f-436f-86bf-57430190c16d	true	id.token.claim
7b3bc654-887f-436f-86bf-57430190c16d	true	access.token.claim
7b3bc654-887f-436f-86bf-57430190c16d	family_name	claim.name
7b3bc654-887f-436f-86bf-57430190c16d	String	jsonType.label
96f00334-04f8-43e0-9339-27f4fea0027f	true	userinfo.token.claim
96f00334-04f8-43e0-9339-27f4fea0027f	middleName	user.attribute
96f00334-04f8-43e0-9339-27f4fea0027f	true	id.token.claim
96f00334-04f8-43e0-9339-27f4fea0027f	true	access.token.claim
96f00334-04f8-43e0-9339-27f4fea0027f	middle_name	claim.name
96f00334-04f8-43e0-9339-27f4fea0027f	String	jsonType.label
d4ccf547-6c21-4358-aa81-00fe4cc47729	true	userinfo.token.claim
d4ccf547-6c21-4358-aa81-00fe4cc47729	firstName	user.attribute
d4ccf547-6c21-4358-aa81-00fe4cc47729	true	id.token.claim
d4ccf547-6c21-4358-aa81-00fe4cc47729	true	access.token.claim
d4ccf547-6c21-4358-aa81-00fe4cc47729	given_name	claim.name
d4ccf547-6c21-4358-aa81-00fe4cc47729	String	jsonType.label
fea87866-cf4e-4548-823c-6ffa1f6847ab	true	userinfo.token.claim
fea87866-cf4e-4548-823c-6ffa1f6847ab	username	user.attribute
fea87866-cf4e-4548-823c-6ffa1f6847ab	true	id.token.claim
fea87866-cf4e-4548-823c-6ffa1f6847ab	true	access.token.claim
fea87866-cf4e-4548-823c-6ffa1f6847ab	preferred_username	claim.name
fea87866-cf4e-4548-823c-6ffa1f6847ab	String	jsonType.label
390d9bb6-492f-41ca-890b-3c740c1a0a50	true	userinfo.token.claim
390d9bb6-492f-41ca-890b-3c740c1a0a50	email	user.attribute
390d9bb6-492f-41ca-890b-3c740c1a0a50	true	id.token.claim
390d9bb6-492f-41ca-890b-3c740c1a0a50	true	access.token.claim
390d9bb6-492f-41ca-890b-3c740c1a0a50	email	claim.name
390d9bb6-492f-41ca-890b-3c740c1a0a50	String	jsonType.label
7be446a8-2348-45f2-87d3-5a652e4506b9	true	userinfo.token.claim
7be446a8-2348-45f2-87d3-5a652e4506b9	emailVerified	user.attribute
7be446a8-2348-45f2-87d3-5a652e4506b9	true	id.token.claim
7be446a8-2348-45f2-87d3-5a652e4506b9	true	access.token.claim
7be446a8-2348-45f2-87d3-5a652e4506b9	email_verified	claim.name
7be446a8-2348-45f2-87d3-5a652e4506b9	boolean	jsonType.label
ac2b8c8b-8eb8-4653-840f-bc76ad785452	formatted	user.attribute.formatted
ac2b8c8b-8eb8-4653-840f-bc76ad785452	country	user.attribute.country
ac2b8c8b-8eb8-4653-840f-bc76ad785452	postal_code	user.attribute.postal_code
ac2b8c8b-8eb8-4653-840f-bc76ad785452	true	userinfo.token.claim
ac2b8c8b-8eb8-4653-840f-bc76ad785452	street	user.attribute.street
ac2b8c8b-8eb8-4653-840f-bc76ad785452	true	id.token.claim
ac2b8c8b-8eb8-4653-840f-bc76ad785452	region	user.attribute.region
ac2b8c8b-8eb8-4653-840f-bc76ad785452	true	access.token.claim
ac2b8c8b-8eb8-4653-840f-bc76ad785452	locality	user.attribute.locality
484ac752-14a5-4e81-a43f-fee964c7d63b	true	userinfo.token.claim
484ac752-14a5-4e81-a43f-fee964c7d63b	phoneNumber	user.attribute
484ac752-14a5-4e81-a43f-fee964c7d63b	true	id.token.claim
484ac752-14a5-4e81-a43f-fee964c7d63b	true	access.token.claim
484ac752-14a5-4e81-a43f-fee964c7d63b	phone_number	claim.name
484ac752-14a5-4e81-a43f-fee964c7d63b	String	jsonType.label
fb588171-037f-43fe-a05e-3682eb4da714	true	userinfo.token.claim
fb588171-037f-43fe-a05e-3682eb4da714	phoneNumberVerified	user.attribute
fb588171-037f-43fe-a05e-3682eb4da714	true	id.token.claim
fb588171-037f-43fe-a05e-3682eb4da714	true	access.token.claim
fb588171-037f-43fe-a05e-3682eb4da714	phone_number_verified	claim.name
fb588171-037f-43fe-a05e-3682eb4da714	boolean	jsonType.label
63bddc57-793b-4ffe-bf12-e7c80ff9567a	true	multivalued
63bddc57-793b-4ffe-bf12-e7c80ff9567a	foo	user.attribute
63bddc57-793b-4ffe-bf12-e7c80ff9567a	true	access.token.claim
63bddc57-793b-4ffe-bf12-e7c80ff9567a	resource_access.${client_id}.roles	claim.name
63bddc57-793b-4ffe-bf12-e7c80ff9567a	String	jsonType.label
bde49369-7aa3-427b-9e14-7e6930033849	true	multivalued
bde49369-7aa3-427b-9e14-7e6930033849	foo	user.attribute
bde49369-7aa3-427b-9e14-7e6930033849	true	access.token.claim
bde49369-7aa3-427b-9e14-7e6930033849	realm_access.roles	claim.name
bde49369-7aa3-427b-9e14-7e6930033849	String	jsonType.label
0aba6eac-129b-4269-a3d5-a17523a4a677	true	multivalued
0aba6eac-129b-4269-a3d5-a17523a4a677	foo	user.attribute
0aba6eac-129b-4269-a3d5-a17523a4a677	true	id.token.claim
0aba6eac-129b-4269-a3d5-a17523a4a677	true	access.token.claim
0aba6eac-129b-4269-a3d5-a17523a4a677	groups	claim.name
0aba6eac-129b-4269-a3d5-a17523a4a677	String	jsonType.label
a4bdf80c-3c41-436f-b8b2-1fa19df1708b	true	userinfo.token.claim
a4bdf80c-3c41-436f-b8b2-1fa19df1708b	username	user.attribute
a4bdf80c-3c41-436f-b8b2-1fa19df1708b	true	id.token.claim
a4bdf80c-3c41-436f-b8b2-1fa19df1708b	true	access.token.claim
a4bdf80c-3c41-436f-b8b2-1fa19df1708b	upn	claim.name
a4bdf80c-3c41-436f-b8b2-1fa19df1708b	String	jsonType.label
7ce6e81d-9182-4136-b074-1b81fc9dbc22	true	id.token.claim
7ce6e81d-9182-4136-b074-1b81fc9dbc22	true	access.token.claim
904c1945-7e8c-4712-b249-45dd70282134	false	single
904c1945-7e8c-4712-b249-45dd70282134	Basic	attribute.nameformat
904c1945-7e8c-4712-b249-45dd70282134	Role	attribute.name
03ec46ec-8816-428e-a718-658ba897b5db	true	userinfo.token.claim
03ec46ec-8816-428e-a718-658ba897b5db	username	user.attribute
03ec46ec-8816-428e-a718-658ba897b5db	true	id.token.claim
03ec46ec-8816-428e-a718-658ba897b5db	true	access.token.claim
03ec46ec-8816-428e-a718-658ba897b5db	preferred_username	claim.name
03ec46ec-8816-428e-a718-658ba897b5db	String	jsonType.label
0f38f27c-15ce-49a2-bec3-fb944c8cfcfb	true	userinfo.token.claim
0f38f27c-15ce-49a2-bec3-fb944c8cfcfb	updatedAt	user.attribute
0f38f27c-15ce-49a2-bec3-fb944c8cfcfb	true	id.token.claim
0f38f27c-15ce-49a2-bec3-fb944c8cfcfb	true	access.token.claim
0f38f27c-15ce-49a2-bec3-fb944c8cfcfb	updated_at	claim.name
0f38f27c-15ce-49a2-bec3-fb944c8cfcfb	long	jsonType.label
4783fc4e-e7d2-49c8-8074-380154c41c96	true	userinfo.token.claim
4783fc4e-e7d2-49c8-8074-380154c41c96	zoneinfo	user.attribute
4783fc4e-e7d2-49c8-8074-380154c41c96	true	id.token.claim
4783fc4e-e7d2-49c8-8074-380154c41c96	true	access.token.claim
4783fc4e-e7d2-49c8-8074-380154c41c96	zoneinfo	claim.name
4783fc4e-e7d2-49c8-8074-380154c41c96	String	jsonType.label
5032e521-ac0f-4b0d-b466-3ea6801f33b2	true	userinfo.token.claim
5032e521-ac0f-4b0d-b466-3ea6801f33b2	lastName	user.attribute
5032e521-ac0f-4b0d-b466-3ea6801f33b2	true	id.token.claim
5032e521-ac0f-4b0d-b466-3ea6801f33b2	true	access.token.claim
5032e521-ac0f-4b0d-b466-3ea6801f33b2	family_name	claim.name
5032e521-ac0f-4b0d-b466-3ea6801f33b2	String	jsonType.label
505ada5c-02db-48d6-9aef-704c3f3a30fe	true	userinfo.token.claim
505ada5c-02db-48d6-9aef-704c3f3a30fe	firstName	user.attribute
505ada5c-02db-48d6-9aef-704c3f3a30fe	true	id.token.claim
505ada5c-02db-48d6-9aef-704c3f3a30fe	true	access.token.claim
505ada5c-02db-48d6-9aef-704c3f3a30fe	given_name	claim.name
505ada5c-02db-48d6-9aef-704c3f3a30fe	String	jsonType.label
66213037-0272-4d85-a26f-20d4441dfa09	true	userinfo.token.claim
66213037-0272-4d85-a26f-20d4441dfa09	gender	user.attribute
66213037-0272-4d85-a26f-20d4441dfa09	true	id.token.claim
66213037-0272-4d85-a26f-20d4441dfa09	true	access.token.claim
66213037-0272-4d85-a26f-20d4441dfa09	gender	claim.name
66213037-0272-4d85-a26f-20d4441dfa09	String	jsonType.label
67a616a9-742d-42d5-8e5f-0612aa502715	true	userinfo.token.claim
67a616a9-742d-42d5-8e5f-0612aa502715	picture	user.attribute
67a616a9-742d-42d5-8e5f-0612aa502715	true	id.token.claim
67a616a9-742d-42d5-8e5f-0612aa502715	true	access.token.claim
67a616a9-742d-42d5-8e5f-0612aa502715	picture	claim.name
67a616a9-742d-42d5-8e5f-0612aa502715	String	jsonType.label
9ef9f827-36b8-47c0-abd5-4464d9946abb	true	userinfo.token.claim
9ef9f827-36b8-47c0-abd5-4464d9946abb	locale	user.attribute
9ef9f827-36b8-47c0-abd5-4464d9946abb	true	id.token.claim
9ef9f827-36b8-47c0-abd5-4464d9946abb	true	access.token.claim
9ef9f827-36b8-47c0-abd5-4464d9946abb	locale	claim.name
9ef9f827-36b8-47c0-abd5-4464d9946abb	String	jsonType.label
be3b3c0c-f06f-46ac-a288-8437b83178e9	true	userinfo.token.claim
be3b3c0c-f06f-46ac-a288-8437b83178e9	middleName	user.attribute
be3b3c0c-f06f-46ac-a288-8437b83178e9	true	id.token.claim
be3b3c0c-f06f-46ac-a288-8437b83178e9	true	access.token.claim
be3b3c0c-f06f-46ac-a288-8437b83178e9	middle_name	claim.name
be3b3c0c-f06f-46ac-a288-8437b83178e9	String	jsonType.label
cce1e042-63d9-4e06-95bd-e689103ebd15	true	userinfo.token.claim
cce1e042-63d9-4e06-95bd-e689103ebd15	profile	user.attribute
cce1e042-63d9-4e06-95bd-e689103ebd15	true	id.token.claim
cce1e042-63d9-4e06-95bd-e689103ebd15	true	access.token.claim
cce1e042-63d9-4e06-95bd-e689103ebd15	profile	claim.name
cce1e042-63d9-4e06-95bd-e689103ebd15	String	jsonType.label
cd1d2cb4-9fb9-4933-a460-7a95f05c2104	true	userinfo.token.claim
cd1d2cb4-9fb9-4933-a460-7a95f05c2104	true	id.token.claim
cd1d2cb4-9fb9-4933-a460-7a95f05c2104	true	access.token.claim
d79a37ee-10df-41b3-a70d-2572ddab66f4	true	userinfo.token.claim
d79a37ee-10df-41b3-a70d-2572ddab66f4	nickname	user.attribute
d79a37ee-10df-41b3-a70d-2572ddab66f4	true	id.token.claim
d79a37ee-10df-41b3-a70d-2572ddab66f4	true	access.token.claim
d79a37ee-10df-41b3-a70d-2572ddab66f4	nickname	claim.name
d79a37ee-10df-41b3-a70d-2572ddab66f4	String	jsonType.label
f2e4729f-4f0d-4178-8a62-5414ecfef515	true	userinfo.token.claim
f2e4729f-4f0d-4178-8a62-5414ecfef515	website	user.attribute
f2e4729f-4f0d-4178-8a62-5414ecfef515	true	id.token.claim
f2e4729f-4f0d-4178-8a62-5414ecfef515	true	access.token.claim
f2e4729f-4f0d-4178-8a62-5414ecfef515	website	claim.name
f2e4729f-4f0d-4178-8a62-5414ecfef515	String	jsonType.label
fcef2e0e-48b2-4570-8daa-b0268b8e16a0	true	userinfo.token.claim
fcef2e0e-48b2-4570-8daa-b0268b8e16a0	birthdate	user.attribute
fcef2e0e-48b2-4570-8daa-b0268b8e16a0	true	id.token.claim
fcef2e0e-48b2-4570-8daa-b0268b8e16a0	true	access.token.claim
fcef2e0e-48b2-4570-8daa-b0268b8e16a0	birthdate	claim.name
fcef2e0e-48b2-4570-8daa-b0268b8e16a0	String	jsonType.label
45260cbd-0a29-438e-a0e0-a5ae2d34042d	true	userinfo.token.claim
45260cbd-0a29-438e-a0e0-a5ae2d34042d	emailVerified	user.attribute
45260cbd-0a29-438e-a0e0-a5ae2d34042d	true	id.token.claim
45260cbd-0a29-438e-a0e0-a5ae2d34042d	true	access.token.claim
45260cbd-0a29-438e-a0e0-a5ae2d34042d	email_verified	claim.name
45260cbd-0a29-438e-a0e0-a5ae2d34042d	boolean	jsonType.label
d0b1be6e-0c28-4f01-a47d-accb8e141bd9	true	userinfo.token.claim
d0b1be6e-0c28-4f01-a47d-accb8e141bd9	email	user.attribute
d0b1be6e-0c28-4f01-a47d-accb8e141bd9	true	id.token.claim
d0b1be6e-0c28-4f01-a47d-accb8e141bd9	true	access.token.claim
d0b1be6e-0c28-4f01-a47d-accb8e141bd9	email	claim.name
d0b1be6e-0c28-4f01-a47d-accb8e141bd9	String	jsonType.label
9d37906a-0670-46b2-9e1a-1622800b6b8a	formatted	user.attribute.formatted
9d37906a-0670-46b2-9e1a-1622800b6b8a	country	user.attribute.country
9d37906a-0670-46b2-9e1a-1622800b6b8a	postal_code	user.attribute.postal_code
9d37906a-0670-46b2-9e1a-1622800b6b8a	true	userinfo.token.claim
9d37906a-0670-46b2-9e1a-1622800b6b8a	street	user.attribute.street
9d37906a-0670-46b2-9e1a-1622800b6b8a	true	id.token.claim
9d37906a-0670-46b2-9e1a-1622800b6b8a	region	user.attribute.region
9d37906a-0670-46b2-9e1a-1622800b6b8a	true	access.token.claim
9d37906a-0670-46b2-9e1a-1622800b6b8a	locality	user.attribute.locality
67a79519-e830-4b88-a2a4-76d53e951f55	true	userinfo.token.claim
67a79519-e830-4b88-a2a4-76d53e951f55	phoneNumberVerified	user.attribute
67a79519-e830-4b88-a2a4-76d53e951f55	true	id.token.claim
67a79519-e830-4b88-a2a4-76d53e951f55	true	access.token.claim
67a79519-e830-4b88-a2a4-76d53e951f55	phone_number_verified	claim.name
67a79519-e830-4b88-a2a4-76d53e951f55	boolean	jsonType.label
a44864c2-dabd-414d-846f-a772eed22e77	true	userinfo.token.claim
a44864c2-dabd-414d-846f-a772eed22e77	phoneNumber	user.attribute
a44864c2-dabd-414d-846f-a772eed22e77	true	id.token.claim
a44864c2-dabd-414d-846f-a772eed22e77	true	access.token.claim
a44864c2-dabd-414d-846f-a772eed22e77	phone_number	claim.name
a44864c2-dabd-414d-846f-a772eed22e77	String	jsonType.label
31a02ffb-1795-4af5-ac7f-861a4744d97b	true	multivalued
31a02ffb-1795-4af5-ac7f-861a4744d97b	foo	user.attribute
31a02ffb-1795-4af5-ac7f-861a4744d97b	true	access.token.claim
31a02ffb-1795-4af5-ac7f-861a4744d97b	resource_access.${client_id}.roles	claim.name
31a02ffb-1795-4af5-ac7f-861a4744d97b	String	jsonType.label
c6417804-4f6e-4481-b42a-86a0619ea2b3	true	multivalued
c6417804-4f6e-4481-b42a-86a0619ea2b3	foo	user.attribute
c6417804-4f6e-4481-b42a-86a0619ea2b3	true	access.token.claim
c6417804-4f6e-4481-b42a-86a0619ea2b3	realm_access.roles	claim.name
c6417804-4f6e-4481-b42a-86a0619ea2b3	String	jsonType.label
5e17d77b-fd29-4d72-a244-a54aca9fa87f	true	multivalued
5e17d77b-fd29-4d72-a244-a54aca9fa87f	foo	user.attribute
5e17d77b-fd29-4d72-a244-a54aca9fa87f	true	id.token.claim
5e17d77b-fd29-4d72-a244-a54aca9fa87f	true	access.token.claim
5e17d77b-fd29-4d72-a244-a54aca9fa87f	groups	claim.name
5e17d77b-fd29-4d72-a244-a54aca9fa87f	String	jsonType.label
aa682483-bab1-44e3-b86a-a385f9a823bb	true	userinfo.token.claim
aa682483-bab1-44e3-b86a-a385f9a823bb	username	user.attribute
aa682483-bab1-44e3-b86a-a385f9a823bb	true	id.token.claim
aa682483-bab1-44e3-b86a-a385f9a823bb	true	access.token.claim
aa682483-bab1-44e3-b86a-a385f9a823bb	upn	claim.name
aa682483-bab1-44e3-b86a-a385f9a823bb	String	jsonType.label
ee4a45b2-83b2-474c-9d21-c10f83c7e08d	true	id.token.claim
ee4a45b2-83b2-474c-9d21-c10f83c7e08d	true	access.token.claim
cea3aeab-5662-49ad-ac36-a1dcfee67276	true	userinfo.token.claim
cea3aeab-5662-49ad-ac36-a1dcfee67276	locale	user.attribute
cea3aeab-5662-49ad-ac36-a1dcfee67276	true	id.token.claim
cea3aeab-5662-49ad-ac36-a1dcfee67276	true	access.token.claim
cea3aeab-5662-49ad-ac36-a1dcfee67276	locale	claim.name
cea3aeab-5662-49ad-ac36-a1dcfee67276	String	jsonType.label
4222907d-7ea1-4910-a533-a15f752ace7c	clientAddress	user.session.note
4222907d-7ea1-4910-a533-a15f752ace7c	true	id.token.claim
4222907d-7ea1-4910-a533-a15f752ace7c	true	access.token.claim
4222907d-7ea1-4910-a533-a15f752ace7c	clientAddress	claim.name
4222907d-7ea1-4910-a533-a15f752ace7c	String	jsonType.label
9ef36950-4d5a-446a-8329-8ab9a9c2d776	client_id	user.session.note
9ef36950-4d5a-446a-8329-8ab9a9c2d776	true	id.token.claim
9ef36950-4d5a-446a-8329-8ab9a9c2d776	true	access.token.claim
9ef36950-4d5a-446a-8329-8ab9a9c2d776	client_id	claim.name
9ef36950-4d5a-446a-8329-8ab9a9c2d776	String	jsonType.label
dc2ded80-844e-4507-be7c-d874ff925271	clientHost	user.session.note
dc2ded80-844e-4507-be7c-d874ff925271	true	id.token.claim
dc2ded80-844e-4507-be7c-d874ff925271	true	access.token.claim
dc2ded80-844e-4507-be7c-d874ff925271	clientHost	claim.name
dc2ded80-844e-4507-be7c-d874ff925271	String	jsonType.label
\.


--
-- Data for Name: realm; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.realm (id, access_code_lifespan, user_action_lifespan, access_token_lifespan, account_theme, admin_theme, email_theme, enabled, events_enabled, events_expiration, login_theme, name, not_before, password_policy, registration_allowed, remember_me, reset_password_allowed, social, ssl_required, sso_idle_timeout, sso_max_lifespan, update_profile_on_soc_login, verify_email, master_admin_client, login_lifespan, internationalization_enabled, default_locale, reg_email_as_username, admin_events_enabled, admin_events_details_enabled, edit_username_allowed, otp_policy_counter, otp_policy_window, otp_policy_period, otp_policy_digits, otp_policy_alg, otp_policy_type, browser_flow, registration_flow, direct_grant_flow, reset_credentials_flow, client_auth_flow, offline_session_idle_timeout, revoke_refresh_token, access_token_life_implicit, login_with_email_allowed, duplicate_emails_allowed, docker_auth_flow, refresh_token_max_reuse, allow_user_managed_access, sso_max_lifespan_remember_me, sso_idle_timeout_remember_me, default_role) FROM stdin;
fc3e583c-0722-4ee1-aeef-70af67cf78ba	60	300	60	\N	\N	\N	t	f	0	\N	master	0	\N	f	f	f	f	EXTERNAL	1800	36000	f	f	2f4a167a-e929-4f60-bb94-eb7a45fdfc31	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	34851a15-ddd8-47e2-8b49-6a04d4d64d89	81cefadf-789e-4571-8c51-a3fba9e5857c	3f198a37-b5ed-4b11-8847-d1937a4fc56e	b51ea154-95e5-4ec4-9547-a74a9f5d36f9	eb100bd9-a3b0-411a-b806-897af36a7bbc	2592000	f	900	t	f	1206965f-dcbd-4871-a2a3-b26b59971301	0	f	0	0	8d754a55-ec91-4fbb-90a4-1061f7104e34
89f9614c-27fd-44d6-b07c-c527669d1d5c	60	300	300	\N	\N	\N	t	f	0	\N	realm-pantanal-dev	0	\N	f	f	f	f	EXTERNAL	1800	36000	f	f	7783f9e8-72cb-461c-a5bd-3a84d75f98dc	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	9d711a7a-1fc5-4553-88ad-a11a4c1115b3	c214dd32-f292-4024-9131-b3facd9d8ff4	b2d9d912-220c-4732-a739-1e3411146d8a	6bd03fd8-d03a-4cfd-98e3-3518d37dbb85	c06887ca-f370-40f0-9204-6b4cff53f219	2592000	f	900	t	f	19c62447-200b-46ea-ac1f-08f03adb4fdb	0	f	0	0	cc85fef9-25b9-4515-98ce-0be4a5df89ac
\.


--
-- Data for Name: realm_attribute; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.realm_attribute (name, realm_id, value) FROM stdin;
_browser_header.contentSecurityPolicyReportOnly	fc3e583c-0722-4ee1-aeef-70af67cf78ba	
_browser_header.xContentTypeOptions	fc3e583c-0722-4ee1-aeef-70af67cf78ba	nosniff
_browser_header.xRobotsTag	fc3e583c-0722-4ee1-aeef-70af67cf78ba	none
_browser_header.xFrameOptions	fc3e583c-0722-4ee1-aeef-70af67cf78ba	SAMEORIGIN
_browser_header.contentSecurityPolicy	fc3e583c-0722-4ee1-aeef-70af67cf78ba	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	fc3e583c-0722-4ee1-aeef-70af67cf78ba	1; mode=block
_browser_header.strictTransportSecurity	fc3e583c-0722-4ee1-aeef-70af67cf78ba	max-age=31536000; includeSubDomains
bruteForceProtected	fc3e583c-0722-4ee1-aeef-70af67cf78ba	false
permanentLockout	fc3e583c-0722-4ee1-aeef-70af67cf78ba	false
maxFailureWaitSeconds	fc3e583c-0722-4ee1-aeef-70af67cf78ba	900
minimumQuickLoginWaitSeconds	fc3e583c-0722-4ee1-aeef-70af67cf78ba	60
waitIncrementSeconds	fc3e583c-0722-4ee1-aeef-70af67cf78ba	60
quickLoginCheckMilliSeconds	fc3e583c-0722-4ee1-aeef-70af67cf78ba	1000
maxDeltaTimeSeconds	fc3e583c-0722-4ee1-aeef-70af67cf78ba	43200
failureFactor	fc3e583c-0722-4ee1-aeef-70af67cf78ba	30
realmReusableOtpCode	fc3e583c-0722-4ee1-aeef-70af67cf78ba	false
displayName	fc3e583c-0722-4ee1-aeef-70af67cf78ba	Keycloak
displayNameHtml	fc3e583c-0722-4ee1-aeef-70af67cf78ba	<div class="kc-logo-text"><span>Keycloak</span></div>
defaultSignatureAlgorithm	fc3e583c-0722-4ee1-aeef-70af67cf78ba	RS256
offlineSessionMaxLifespanEnabled	fc3e583c-0722-4ee1-aeef-70af67cf78ba	false
offlineSessionMaxLifespan	fc3e583c-0722-4ee1-aeef-70af67cf78ba	5184000
_browser_header.contentSecurityPolicyReportOnly	89f9614c-27fd-44d6-b07c-c527669d1d5c	
_browser_header.xContentTypeOptions	89f9614c-27fd-44d6-b07c-c527669d1d5c	nosniff
_browser_header.xRobotsTag	89f9614c-27fd-44d6-b07c-c527669d1d5c	none
_browser_header.xFrameOptions	89f9614c-27fd-44d6-b07c-c527669d1d5c	SAMEORIGIN
_browser_header.contentSecurityPolicy	89f9614c-27fd-44d6-b07c-c527669d1d5c	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	89f9614c-27fd-44d6-b07c-c527669d1d5c	1; mode=block
_browser_header.strictTransportSecurity	89f9614c-27fd-44d6-b07c-c527669d1d5c	max-age=31536000; includeSubDomains
bruteForceProtected	89f9614c-27fd-44d6-b07c-c527669d1d5c	false
permanentLockout	89f9614c-27fd-44d6-b07c-c527669d1d5c	false
maxFailureWaitSeconds	89f9614c-27fd-44d6-b07c-c527669d1d5c	900
minimumQuickLoginWaitSeconds	89f9614c-27fd-44d6-b07c-c527669d1d5c	60
waitIncrementSeconds	89f9614c-27fd-44d6-b07c-c527669d1d5c	60
quickLoginCheckMilliSeconds	89f9614c-27fd-44d6-b07c-c527669d1d5c	1000
maxDeltaTimeSeconds	89f9614c-27fd-44d6-b07c-c527669d1d5c	43200
failureFactor	89f9614c-27fd-44d6-b07c-c527669d1d5c	30
realmReusableOtpCode	89f9614c-27fd-44d6-b07c-c527669d1d5c	false
defaultSignatureAlgorithm	89f9614c-27fd-44d6-b07c-c527669d1d5c	RS256
offlineSessionMaxLifespanEnabled	89f9614c-27fd-44d6-b07c-c527669d1d5c	false
offlineSessionMaxLifespan	89f9614c-27fd-44d6-b07c-c527669d1d5c	5184000
actionTokenGeneratedByAdminLifespan	89f9614c-27fd-44d6-b07c-c527669d1d5c	43200
actionTokenGeneratedByUserLifespan	89f9614c-27fd-44d6-b07c-c527669d1d5c	300
oauth2DeviceCodeLifespan	89f9614c-27fd-44d6-b07c-c527669d1d5c	600
oauth2DevicePollingInterval	89f9614c-27fd-44d6-b07c-c527669d1d5c	5
webAuthnPolicyRpEntityName	89f9614c-27fd-44d6-b07c-c527669d1d5c	keycloak
webAuthnPolicySignatureAlgorithms	89f9614c-27fd-44d6-b07c-c527669d1d5c	ES256
webAuthnPolicyRpId	89f9614c-27fd-44d6-b07c-c527669d1d5c	
webAuthnPolicyAttestationConveyancePreference	89f9614c-27fd-44d6-b07c-c527669d1d5c	not specified
webAuthnPolicyAuthenticatorAttachment	89f9614c-27fd-44d6-b07c-c527669d1d5c	not specified
webAuthnPolicyRequireResidentKey	89f9614c-27fd-44d6-b07c-c527669d1d5c	not specified
webAuthnPolicyUserVerificationRequirement	89f9614c-27fd-44d6-b07c-c527669d1d5c	not specified
webAuthnPolicyCreateTimeout	89f9614c-27fd-44d6-b07c-c527669d1d5c	0
webAuthnPolicyAvoidSameAuthenticatorRegister	89f9614c-27fd-44d6-b07c-c527669d1d5c	false
webAuthnPolicyRpEntityNamePasswordless	89f9614c-27fd-44d6-b07c-c527669d1d5c	keycloak
webAuthnPolicySignatureAlgorithmsPasswordless	89f9614c-27fd-44d6-b07c-c527669d1d5c	ES256
webAuthnPolicyRpIdPasswordless	89f9614c-27fd-44d6-b07c-c527669d1d5c	
webAuthnPolicyAttestationConveyancePreferencePasswordless	89f9614c-27fd-44d6-b07c-c527669d1d5c	not specified
webAuthnPolicyAuthenticatorAttachmentPasswordless	89f9614c-27fd-44d6-b07c-c527669d1d5c	not specified
webAuthnPolicyRequireResidentKeyPasswordless	89f9614c-27fd-44d6-b07c-c527669d1d5c	not specified
webAuthnPolicyUserVerificationRequirementPasswordless	89f9614c-27fd-44d6-b07c-c527669d1d5c	not specified
webAuthnPolicyCreateTimeoutPasswordless	89f9614c-27fd-44d6-b07c-c527669d1d5c	0
webAuthnPolicyAvoidSameAuthenticatorRegisterPasswordless	89f9614c-27fd-44d6-b07c-c527669d1d5c	false
cibaBackchannelTokenDeliveryMode	89f9614c-27fd-44d6-b07c-c527669d1d5c	poll
cibaExpiresIn	89f9614c-27fd-44d6-b07c-c527669d1d5c	120
cibaInterval	89f9614c-27fd-44d6-b07c-c527669d1d5c	5
cibaAuthRequestedUserHint	89f9614c-27fd-44d6-b07c-c527669d1d5c	login_hint
parRequestUriLifespan	89f9614c-27fd-44d6-b07c-c527669d1d5c	60
\.


--
-- Data for Name: realm_default_groups; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.realm_default_groups (realm_id, group_id) FROM stdin;
\.


--
-- Data for Name: realm_enabled_event_types; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.realm_enabled_event_types (realm_id, value) FROM stdin;
\.


--
-- Data for Name: realm_events_listeners; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.realm_events_listeners (realm_id, value) FROM stdin;
fc3e583c-0722-4ee1-aeef-70af67cf78ba	jboss-logging
89f9614c-27fd-44d6-b07c-c527669d1d5c	jboss-logging
\.


--
-- Data for Name: realm_localizations; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.realm_localizations (realm_id, locale, texts) FROM stdin;
\.


--
-- Data for Name: realm_required_credential; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.realm_required_credential (type, form_label, input, secret, realm_id) FROM stdin;
password	password	t	t	fc3e583c-0722-4ee1-aeef-70af67cf78ba
password	password	t	t	89f9614c-27fd-44d6-b07c-c527669d1d5c
\.


--
-- Data for Name: realm_smtp_config; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.realm_smtp_config (realm_id, value, name) FROM stdin;
\.


--
-- Data for Name: realm_supported_locales; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.realm_supported_locales (realm_id, value) FROM stdin;
\.


--
-- Data for Name: redirect_uris; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.redirect_uris (client_id, value) FROM stdin;
7626857f-0274-4b0c-90bc-996f940fc6d9	/realms/master/account/*
8f880efc-b681-4dd1-bc3f-a71b772ef16b	/realms/master/account/*
194b8b1c-ff39-4947-bac4-a35411a838fd	/admin/master/console/*
d1b8d38a-ab09-45f8-acfe-c5d933efbbe0	/realms/realm-pantanal-dev/account/*
c559f39f-2212-4360-83ed-4829e52c3e24	/realms/realm-pantanal-dev/account/*
1b41c01a-a904-41a8-9c76-88a152c41ac5	/admin/realm-pantanal-dev/console/*
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	http://localhost:3001/*
\.


--
-- Data for Name: required_action_config; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.required_action_config (required_action_id, value, name) FROM stdin;
\.


--
-- Data for Name: required_action_provider; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.required_action_provider (id, alias, name, realm_id, enabled, default_action, provider_id, priority) FROM stdin;
84e92e22-ddf9-4246-9366-63fb8d0e9db4	VERIFY_EMAIL	Verify Email	fc3e583c-0722-4ee1-aeef-70af67cf78ba	t	f	VERIFY_EMAIL	50
b9f3ca05-4b10-4d45-80b2-ef2ea8e1ea39	UPDATE_PROFILE	Update Profile	fc3e583c-0722-4ee1-aeef-70af67cf78ba	t	f	UPDATE_PROFILE	40
b4abf3d8-4dc6-43de-b39d-ff1e307840b9	CONFIGURE_TOTP	Configure OTP	fc3e583c-0722-4ee1-aeef-70af67cf78ba	t	f	CONFIGURE_TOTP	10
55e7c113-8d3f-4c65-8e7e-241c0cfef42d	UPDATE_PASSWORD	Update Password	fc3e583c-0722-4ee1-aeef-70af67cf78ba	t	f	UPDATE_PASSWORD	30
b846bf27-83d6-4d91-96c6-2940e05403c3	TERMS_AND_CONDITIONS	Terms and Conditions	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f	f	TERMS_AND_CONDITIONS	20
26bb75ca-0b38-4852-8c9e-68fbe51b9356	delete_account	Delete Account	fc3e583c-0722-4ee1-aeef-70af67cf78ba	f	f	delete_account	60
df80f1f4-3e5f-4282-bb86-640913ed98e1	update_user_locale	Update User Locale	fc3e583c-0722-4ee1-aeef-70af67cf78ba	t	f	update_user_locale	1000
33eb7db0-74fb-4041-8b6e-5433fec77e44	webauthn-register	Webauthn Register	fc3e583c-0722-4ee1-aeef-70af67cf78ba	t	f	webauthn-register	70
663ff27b-2642-435c-b3a1-e0bfe7468fd9	webauthn-register-passwordless	Webauthn Register Passwordless	fc3e583c-0722-4ee1-aeef-70af67cf78ba	t	f	webauthn-register-passwordless	80
85fd193a-1a12-404f-b866-fa0d9d2bf198	VERIFY_EMAIL	Verify Email	89f9614c-27fd-44d6-b07c-c527669d1d5c	t	f	VERIFY_EMAIL	50
5d4db732-11fd-4684-b5f6-70cca29d5720	UPDATE_PROFILE	Update Profile	89f9614c-27fd-44d6-b07c-c527669d1d5c	t	f	UPDATE_PROFILE	40
2abd6e49-9e9a-4a3b-8f37-f43e77563772	CONFIGURE_TOTP	Configure OTP	89f9614c-27fd-44d6-b07c-c527669d1d5c	t	f	CONFIGURE_TOTP	10
99a5a02c-d445-48e4-bdb3-f30fe3c12435	UPDATE_PASSWORD	Update Password	89f9614c-27fd-44d6-b07c-c527669d1d5c	t	f	UPDATE_PASSWORD	30
a3dad5b9-9bc8-4038-a825-bbfc08fed0a5	TERMS_AND_CONDITIONS	Terms and Conditions	89f9614c-27fd-44d6-b07c-c527669d1d5c	f	f	TERMS_AND_CONDITIONS	20
4424836d-c55d-42ae-a3b8-afd1eff55c28	delete_account	Delete Account	89f9614c-27fd-44d6-b07c-c527669d1d5c	f	f	delete_account	60
d1c91c76-24e1-49fc-9a79-5ebef22ed523	update_user_locale	Update User Locale	89f9614c-27fd-44d6-b07c-c527669d1d5c	t	f	update_user_locale	1000
3ca054df-b8df-4b48-8ac8-025503804e90	webauthn-register	Webauthn Register	89f9614c-27fd-44d6-b07c-c527669d1d5c	t	f	webauthn-register	70
037f8da6-59e2-4efb-9e80-cd438fe07cbf	webauthn-register-passwordless	Webauthn Register Passwordless	89f9614c-27fd-44d6-b07c-c527669d1d5c	t	f	webauthn-register-passwordless	80
\.


--
-- Data for Name: resource_attribute; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.resource_attribute (id, name, value, resource_id) FROM stdin;
\.


--
-- Data for Name: resource_policy; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.resource_policy (resource_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_scope; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.resource_scope (resource_id, scope_id) FROM stdin;
\.


--
-- Data for Name: resource_server; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.resource_server (id, allow_rs_remote_mgmt, policy_enforce_mode, decision_strategy) FROM stdin;
\.


--
-- Data for Name: resource_server_perm_ticket; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.resource_server_perm_ticket (id, owner, requester, created_timestamp, granted_timestamp, resource_id, scope_id, resource_server_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_server_policy; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.resource_server_policy (id, name, description, type, decision_strategy, logic, resource_server_id, owner) FROM stdin;
\.


--
-- Data for Name: resource_server_resource; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.resource_server_resource (id, name, type, icon_uri, owner, resource_server_id, owner_managed_access, display_name) FROM stdin;
\.


--
-- Data for Name: resource_server_scope; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.resource_server_scope (id, name, icon_uri, resource_server_id, display_name) FROM stdin;
\.


--
-- Data for Name: resource_uris; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.resource_uris (resource_id, value) FROM stdin;
\.


--
-- Data for Name: role_attribute; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.role_attribute (id, role_id, name, value) FROM stdin;
\.


--
-- Data for Name: scope_mapping; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.scope_mapping (client_id, role_id) FROM stdin;
8f880efc-b681-4dd1-bc3f-a71b772ef16b	76fa9fe2-9cd4-4539-b375-b3ac1e3d85b5
8f880efc-b681-4dd1-bc3f-a71b772ef16b	54d34dc7-47a9-451f-8a50-095fe4621401
c559f39f-2212-4360-83ed-4829e52c3e24	cb51f8b0-0f6f-401e-8bc8-eb247e2b6fed
c559f39f-2212-4360-83ed-4829e52c3e24	e9add83b-98b2-4060-aecd-7e80521f91b4
\.


--
-- Data for Name: scope_policy; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.scope_policy (scope_id, policy_id) FROM stdin;
\.


--
-- Data for Name: user_attribute; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_attribute (name, value, user_id, id) FROM stdin;
cpf	234.531.920-87	065872c1-177f-41be-9ac0-3221146ad40b	a1670e79-13c5-40a8-8fda-cecef36e5085
\.


--
-- Data for Name: user_consent; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_consent (id, client_id, user_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: user_consent_client_scope; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_consent_client_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: user_entity; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_entity (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before) FROM stdin;
408dc63c-a5d8-495c-9f54-0ac8cfd6dcff	\N	6930d86e-91b5-4acd-a989-f96279472bda	f	t	\N	\N	\N	fc3e583c-0722-4ee1-aeef-70af67cf78ba	admin	1695174962099	\N	0
fcdae981-e258-4cd4-b0b8-e437cae8fdd9	\N	86d22052-6601-456f-919f-6f82a67568e3	f	t	\N	\N	\N	89f9614c-27fd-44d6-b07c-c527669d1d5c	service-account-client-id-backend-1	1695175050728	ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	0
065872c1-177f-41be-9ac0-3221146ad40b	\N	fb36bea7-a42d-4cea-93b9-8ac4f1696a5b	f	t	\N			89f9614c-27fd-44d6-b07c-c527669d1d5c	funcionario1	1695175108523	\N	0
\.


--
-- Data for Name: user_federation_config; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_federation_config (user_federation_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_federation_mapper (id, name, federation_provider_id, federation_mapper_type, realm_id) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper_config; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_federation_mapper_config (user_federation_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_provider; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_federation_provider (id, changed_sync_period, display_name, full_sync_period, last_sync, priority, provider_name, realm_id) FROM stdin;
\.


--
-- Data for Name: user_group_membership; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_group_membership (group_id, user_id) FROM stdin;
\.


--
-- Data for Name: user_required_action; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_required_action (user_id, required_action) FROM stdin;
\.


--
-- Data for Name: user_role_mapping; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_role_mapping (role_id, user_id) FROM stdin;
8d754a55-ec91-4fbb-90a4-1061f7104e34	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
b8b5f269-dbfc-47d2-8a31-516c0ad457aa	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
84a57036-c660-46e9-8269-e65339589114	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
2c7dfd6f-8b67-417e-9ca3-2f2515133738	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
4bac61f4-8b7d-4d79-923e-caa7c51b2390	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
f35f20c6-1468-4f81-bc57-6336369c1f2a	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
f66ef8c0-440e-4110-9475-e6bf6921288a	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
323d8c47-6191-4afc-89f7-ed4da4f34171	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
0ea30235-713a-492f-8e55-3e786e3703aa	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
72e05d0a-f850-4c8b-a6f9-7fa865fa8c12	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
185454c3-3bc4-4589-b1e9-f0817dfd7878	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
4c38fb49-9048-44de-b22b-ba5195f737a2	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
99539257-f641-4395-97c0-1fce95793597	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
f9b9699d-be45-4f29-9a10-c937ceba33e2	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
664ecbb7-bfcf-492f-9200-311da3360358	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
6e7e357f-90a9-488d-8496-73d8799800d7	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
d797e279-0d71-46a9-8a9a-03aca51cd633	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
a27ff082-2d2b-4130-8e9a-bdd8eea1c286	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
fcfa2fc5-8e27-4f43-9884-dc750fcba5fd	408dc63c-a5d8-495c-9f54-0ac8cfd6dcff
cc85fef9-25b9-4515-98ce-0be4a5df89ac	fcdae981-e258-4cd4-b0b8-e437cae8fdd9
cc85fef9-25b9-4515-98ce-0be4a5df89ac	065872c1-177f-41be-9ac0-3221146ad40b
8eb3be08-fd48-4e0b-8a58-7488f1a8f69c	065872c1-177f-41be-9ac0-3221146ad40b
85c6dc54-2fc5-4c52-b82c-1d17b61328f0	065872c1-177f-41be-9ac0-3221146ad40b
b13c9ab4-140d-4195-906d-d599a431a6f8	065872c1-177f-41be-9ac0-3221146ad40b
19554cab-896e-4183-8096-e541a4f5d051	065872c1-177f-41be-9ac0-3221146ad40b
057baeb4-3a85-4816-9551-c809c7f96b51	065872c1-177f-41be-9ac0-3221146ad40b
c94a3122-79b2-42a2-8190-a8070f79ea51	065872c1-177f-41be-9ac0-3221146ad40b
e1f65982-48b3-48a7-94de-bc487a1d39e0	065872c1-177f-41be-9ac0-3221146ad40b
9c98d677-c8f8-418e-8929-3093bba6d755	065872c1-177f-41be-9ac0-3221146ad40b
836205f2-2323-46e9-ab27-121cb3593371	065872c1-177f-41be-9ac0-3221146ad40b
cd5b9411-3733-4d7e-9348-127592db0beb	065872c1-177f-41be-9ac0-3221146ad40b
e003249d-98e8-4e0c-b11a-6161e53a2a07	065872c1-177f-41be-9ac0-3221146ad40b
e51f0b1e-547e-4817-bd74-335b274fd0c1	065872c1-177f-41be-9ac0-3221146ad40b
f11ee328-716b-429f-94f3-a885e06bb1b1	065872c1-177f-41be-9ac0-3221146ad40b
8ea07074-4203-4e0c-b716-241b4475de7f	065872c1-177f-41be-9ac0-3221146ad40b
4b0f62a5-156c-4327-9984-5381cd7aa440	065872c1-177f-41be-9ac0-3221146ad40b
174b809c-a0bd-4420-a65e-106b7192df47	065872c1-177f-41be-9ac0-3221146ad40b
6af6f895-1117-42f5-89d0-f823fdca15d4	065872c1-177f-41be-9ac0-3221146ad40b
abdf3ba6-ca04-4c81-ae3f-d252d345e24b	065872c1-177f-41be-9ac0-3221146ad40b
b99a6f9d-ab79-4dce-9b33-a414af25dafc	065872c1-177f-41be-9ac0-3221146ad40b
13f279f5-94d5-4511-91e8-855870cd4882	065872c1-177f-41be-9ac0-3221146ad40b
a8fdd752-e5e5-4c0c-a909-344192edc59d	065872c1-177f-41be-9ac0-3221146ad40b
fb640fe1-20fa-47d0-95fe-c773b55ab9ef	065872c1-177f-41be-9ac0-3221146ad40b
83d5f6e8-816f-4051-b65e-71bd11fa28fa	065872c1-177f-41be-9ac0-3221146ad40b
529e1198-678b-428b-864c-e4dc79c6b20a	065872c1-177f-41be-9ac0-3221146ad40b
ea28a649-4409-405f-a833-47b993b90197	065872c1-177f-41be-9ac0-3221146ad40b
0ab41a31-20a4-4e4a-a77d-e6e44f3b0122	065872c1-177f-41be-9ac0-3221146ad40b
4a490a09-61ac-4c3a-9119-045fe3bf0a67	065872c1-177f-41be-9ac0-3221146ad40b
df625d4b-3286-42d2-9ae5-531b78dadd4f	065872c1-177f-41be-9ac0-3221146ad40b
b2974e49-f098-4d80-a7e4-0c307a7fcc15	065872c1-177f-41be-9ac0-3221146ad40b
e9add83b-98b2-4060-aecd-7e80521f91b4	065872c1-177f-41be-9ac0-3221146ad40b
cd5f6c22-798c-4711-b35b-ebea1cddd37e	065872c1-177f-41be-9ac0-3221146ad40b
e6f38089-98db-43cd-97cb-ca647734bee0	065872c1-177f-41be-9ac0-3221146ad40b
cb51f8b0-0f6f-401e-8bc8-eb247e2b6fed	065872c1-177f-41be-9ac0-3221146ad40b
62689410-a0e3-47de-b733-64783de4a697	065872c1-177f-41be-9ac0-3221146ad40b
0edbf0a2-3eec-4654-97e5-7f1860c7e2d1	065872c1-177f-41be-9ac0-3221146ad40b
c0c9eae3-28a9-4588-ad41-2166e88a1bb9	065872c1-177f-41be-9ac0-3221146ad40b
8eb3be08-fd48-4e0b-8a58-7488f1a8f69c	fcdae981-e258-4cd4-b0b8-e437cae8fdd9
85c6dc54-2fc5-4c52-b82c-1d17b61328f0	fcdae981-e258-4cd4-b0b8-e437cae8fdd9
b13c9ab4-140d-4195-906d-d599a431a6f8	fcdae981-e258-4cd4-b0b8-e437cae8fdd9
19554cab-896e-4183-8096-e541a4f5d051	fcdae981-e258-4cd4-b0b8-e437cae8fdd9
057baeb4-3a85-4816-9551-c809c7f96b51	fcdae981-e258-4cd4-b0b8-e437cae8fdd9
c94a3122-79b2-42a2-8190-a8070f79ea51	fcdae981-e258-4cd4-b0b8-e437cae8fdd9
e1f65982-48b3-48a7-94de-bc487a1d39e0	fcdae981-e258-4cd4-b0b8-e437cae8fdd9
9c98d677-c8f8-418e-8929-3093bba6d755	fcdae981-e258-4cd4-b0b8-e437cae8fdd9
\.


--
-- Data for Name: user_session; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_session (id, auth_method, ip_address, last_session_refresh, login_username, realm_id, remember_me, started, user_id, user_session_state, broker_session_id, broker_user_id) FROM stdin;
\.


--
-- Data for Name: user_session_note; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.user_session_note (user_session, name, value) FROM stdin;
\.


--
-- Data for Name: username_login_failure; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.username_login_failure (realm_id, username, failed_login_not_before, last_failure, last_ip_failure, num_failures) FROM stdin;
\.


--
-- Data for Name: web_origins; Type: TABLE DATA; Schema: public; Owner: dev_user
--

COPY public.web_origins (client_id, value) FROM stdin;
194b8b1c-ff39-4947-bac4-a35411a838fd	+
1b41c01a-a904-41a8-9c76-88a152c41ac5	+
ef2b8215-0be5-4964-a5fd-9fbb38d4e1c6	*
\.


--
-- Name: username_login_failure CONSTRAINT_17-2; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.username_login_failure
    ADD CONSTRAINT "CONSTRAINT_17-2" PRIMARY KEY (realm_id, username);


--
-- Name: keycloak_role UK_J3RWUVD56ONTGSUHOGM184WW2-2; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT "UK_J3RWUVD56ONTGSUHOGM184WW2-2" UNIQUE (name, client_realm_constraint);


--
-- Name: client_auth_flow_bindings c_cli_flow_bind; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_auth_flow_bindings
    ADD CONSTRAINT c_cli_flow_bind PRIMARY KEY (client_id, binding_name);


--
-- Name: client_scope_client c_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_scope_client
    ADD CONSTRAINT c_cli_scope_bind PRIMARY KEY (client_id, scope_id);


--
-- Name: client_initial_access cnstr_client_init_acc_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT cnstr_client_init_acc_pk PRIMARY KEY (id);


--
-- Name: realm_default_groups con_group_id_def_groups; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT con_group_id_def_groups UNIQUE (group_id);


--
-- Name: broker_link constr_broker_link_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.broker_link
    ADD CONSTRAINT constr_broker_link_pk PRIMARY KEY (identity_provider, user_id);


--
-- Name: client_user_session_note constr_cl_usr_ses_note; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_user_session_note
    ADD CONSTRAINT constr_cl_usr_ses_note PRIMARY KEY (client_session, name);


--
-- Name: component_config constr_component_config_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT constr_component_config_pk PRIMARY KEY (id);


--
-- Name: component constr_component_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT constr_component_pk PRIMARY KEY (id);


--
-- Name: fed_user_required_action constr_fed_required_action; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.fed_user_required_action
    ADD CONSTRAINT constr_fed_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: fed_user_attribute constr_fed_user_attr_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.fed_user_attribute
    ADD CONSTRAINT constr_fed_user_attr_pk PRIMARY KEY (id);


--
-- Name: fed_user_consent constr_fed_user_consent_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.fed_user_consent
    ADD CONSTRAINT constr_fed_user_consent_pk PRIMARY KEY (id);


--
-- Name: fed_user_credential constr_fed_user_cred_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.fed_user_credential
    ADD CONSTRAINT constr_fed_user_cred_pk PRIMARY KEY (id);


--
-- Name: fed_user_group_membership constr_fed_user_group; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.fed_user_group_membership
    ADD CONSTRAINT constr_fed_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: fed_user_role_mapping constr_fed_user_role; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.fed_user_role_mapping
    ADD CONSTRAINT constr_fed_user_role PRIMARY KEY (role_id, user_id);


--
-- Name: federated_user constr_federated_user; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.federated_user
    ADD CONSTRAINT constr_federated_user PRIMARY KEY (id);


--
-- Name: realm_default_groups constr_realm_default_groups; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT constr_realm_default_groups PRIMARY KEY (realm_id, group_id);


--
-- Name: realm_enabled_event_types constr_realm_enabl_event_types; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT constr_realm_enabl_event_types PRIMARY KEY (realm_id, value);


--
-- Name: realm_events_listeners constr_realm_events_listeners; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT constr_realm_events_listeners PRIMARY KEY (realm_id, value);


--
-- Name: realm_supported_locales constr_realm_supported_locales; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT constr_realm_supported_locales PRIMARY KEY (realm_id, value);


--
-- Name: identity_provider constraint_2b; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT constraint_2b PRIMARY KEY (internal_id);


--
-- Name: client_attributes constraint_3c; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT constraint_3c PRIMARY KEY (client_id, name);


--
-- Name: event_entity constraint_4; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.event_entity
    ADD CONSTRAINT constraint_4 PRIMARY KEY (id);


--
-- Name: federated_identity constraint_40; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT constraint_40 PRIMARY KEY (identity_provider, user_id);


--
-- Name: realm constraint_4a; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT constraint_4a PRIMARY KEY (id);


--
-- Name: client_session_role constraint_5; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_session_role
    ADD CONSTRAINT constraint_5 PRIMARY KEY (client_session, role_id);


--
-- Name: user_session constraint_57; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_session
    ADD CONSTRAINT constraint_57 PRIMARY KEY (id);


--
-- Name: user_federation_provider constraint_5c; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT constraint_5c PRIMARY KEY (id);


--
-- Name: client_session_note constraint_5e; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_session_note
    ADD CONSTRAINT constraint_5e PRIMARY KEY (client_session, name);


--
-- Name: client constraint_7; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT constraint_7 PRIMARY KEY (id);


--
-- Name: client_session constraint_8; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_session
    ADD CONSTRAINT constraint_8 PRIMARY KEY (id);


--
-- Name: scope_mapping constraint_81; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT constraint_81 PRIMARY KEY (client_id, role_id);


--
-- Name: client_node_registrations constraint_84; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT constraint_84 PRIMARY KEY (client_id, name);


--
-- Name: realm_attribute constraint_9; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT constraint_9 PRIMARY KEY (name, realm_id);


--
-- Name: realm_required_credential constraint_92; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT constraint_92 PRIMARY KEY (realm_id, type);


--
-- Name: keycloak_role constraint_a; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT constraint_a PRIMARY KEY (id);


--
-- Name: admin_event_entity constraint_admin_event_entity; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.admin_event_entity
    ADD CONSTRAINT constraint_admin_event_entity PRIMARY KEY (id);


--
-- Name: authenticator_config_entry constraint_auth_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.authenticator_config_entry
    ADD CONSTRAINT constraint_auth_cfg_pk PRIMARY KEY (authenticator_id, name);


--
-- Name: authentication_execution constraint_auth_exec_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT constraint_auth_exec_pk PRIMARY KEY (id);


--
-- Name: authentication_flow constraint_auth_flow_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT constraint_auth_flow_pk PRIMARY KEY (id);


--
-- Name: authenticator_config constraint_auth_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT constraint_auth_pk PRIMARY KEY (id);


--
-- Name: client_session_auth_status constraint_auth_status_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_session_auth_status
    ADD CONSTRAINT constraint_auth_status_pk PRIMARY KEY (client_session, authenticator);


--
-- Name: user_role_mapping constraint_c; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT constraint_c PRIMARY KEY (role_id, user_id);


--
-- Name: composite_role constraint_composite_role; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT constraint_composite_role PRIMARY KEY (composite, child_role);


--
-- Name: client_session_prot_mapper constraint_cs_pmp_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_session_prot_mapper
    ADD CONSTRAINT constraint_cs_pmp_pk PRIMARY KEY (client_session, protocol_mapper_id);


--
-- Name: identity_provider_config constraint_d; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT constraint_d PRIMARY KEY (identity_provider_id, name);


--
-- Name: policy_config constraint_dpc; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT constraint_dpc PRIMARY KEY (policy_id, name);


--
-- Name: realm_smtp_config constraint_e; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT constraint_e PRIMARY KEY (realm_id, name);


--
-- Name: credential constraint_f; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT constraint_f PRIMARY KEY (id);


--
-- Name: user_federation_config constraint_f9; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT constraint_f9 PRIMARY KEY (user_federation_provider_id, name);


--
-- Name: resource_server_perm_ticket constraint_fapmt; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT constraint_fapmt PRIMARY KEY (id);


--
-- Name: resource_server_resource constraint_farsr; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT constraint_farsr PRIMARY KEY (id);


--
-- Name: resource_server_policy constraint_farsrp; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT constraint_farsrp PRIMARY KEY (id);


--
-- Name: associated_policy constraint_farsrpap; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT constraint_farsrpap PRIMARY KEY (policy_id, associated_policy_id);


--
-- Name: resource_policy constraint_farsrpp; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT constraint_farsrpp PRIMARY KEY (resource_id, policy_id);


--
-- Name: resource_server_scope constraint_farsrs; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT constraint_farsrs PRIMARY KEY (id);


--
-- Name: resource_scope constraint_farsrsp; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT constraint_farsrsp PRIMARY KEY (resource_id, scope_id);


--
-- Name: scope_policy constraint_farsrsps; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT constraint_farsrsps PRIMARY KEY (scope_id, policy_id);


--
-- Name: user_entity constraint_fb; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT constraint_fb PRIMARY KEY (id);


--
-- Name: user_federation_mapper_config constraint_fedmapper_cfg_pm; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT constraint_fedmapper_cfg_pm PRIMARY KEY (user_federation_mapper_id, name);


--
-- Name: user_federation_mapper constraint_fedmapperpm; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT constraint_fedmapperpm PRIMARY KEY (id);


--
-- Name: fed_user_consent_cl_scope constraint_fgrntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.fed_user_consent_cl_scope
    ADD CONSTRAINT constraint_fgrntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent_client_scope constraint_grntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT constraint_grntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent constraint_grntcsnt_pm; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT constraint_grntcsnt_pm PRIMARY KEY (id);


--
-- Name: keycloak_group constraint_group; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT constraint_group PRIMARY KEY (id);


--
-- Name: group_attribute constraint_group_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT constraint_group_attribute_pk PRIMARY KEY (id);


--
-- Name: group_role_mapping constraint_group_role; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT constraint_group_role PRIMARY KEY (role_id, group_id);


--
-- Name: identity_provider_mapper constraint_idpm; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT constraint_idpm PRIMARY KEY (id);


--
-- Name: idp_mapper_config constraint_idpmconfig; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT constraint_idpmconfig PRIMARY KEY (idp_mapper_id, name);


--
-- Name: migration_model constraint_migmod; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT constraint_migmod PRIMARY KEY (id);


--
-- Name: offline_client_session constraint_offl_cl_ses_pk3; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.offline_client_session
    ADD CONSTRAINT constraint_offl_cl_ses_pk3 PRIMARY KEY (user_session_id, client_id, client_storage_provider, external_client_id, offline_flag);


--
-- Name: offline_user_session constraint_offl_us_ses_pk2; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.offline_user_session
    ADD CONSTRAINT constraint_offl_us_ses_pk2 PRIMARY KEY (user_session_id, offline_flag);


--
-- Name: protocol_mapper constraint_pcm; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT constraint_pcm PRIMARY KEY (id);


--
-- Name: protocol_mapper_config constraint_pmconfig; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT constraint_pmconfig PRIMARY KEY (protocol_mapper_id, name);


--
-- Name: redirect_uris constraint_redirect_uris; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT constraint_redirect_uris PRIMARY KEY (client_id, value);


--
-- Name: required_action_config constraint_req_act_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.required_action_config
    ADD CONSTRAINT constraint_req_act_cfg_pk PRIMARY KEY (required_action_id, name);


--
-- Name: required_action_provider constraint_req_act_prv_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT constraint_req_act_prv_pk PRIMARY KEY (id);


--
-- Name: user_required_action constraint_required_action; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT constraint_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: resource_uris constraint_resour_uris_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT constraint_resour_uris_pk PRIMARY KEY (resource_id, value);


--
-- Name: role_attribute constraint_role_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT constraint_role_attribute_pk PRIMARY KEY (id);


--
-- Name: user_attribute constraint_user_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT constraint_user_attribute_pk PRIMARY KEY (id);


--
-- Name: user_group_membership constraint_user_group; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT constraint_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: user_session_note constraint_usn_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_session_note
    ADD CONSTRAINT constraint_usn_pk PRIMARY KEY (user_session, name);


--
-- Name: web_origins constraint_web_origins; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT constraint_web_origins PRIMARY KEY (client_id, value);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: client_scope_attributes pk_cl_tmpl_attr; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT pk_cl_tmpl_attr PRIMARY KEY (scope_id, name);


--
-- Name: client_scope pk_cli_template; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT pk_cli_template PRIMARY KEY (id);


--
-- Name: resource_server pk_resource_server; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server
    ADD CONSTRAINT pk_resource_server PRIMARY KEY (id);


--
-- Name: client_scope_role_mapping pk_template_scope; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT pk_template_scope PRIMARY KEY (scope_id, role_id);


--
-- Name: default_client_scope r_def_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT r_def_cli_scope_bind PRIMARY KEY (realm_id, scope_id);


--
-- Name: realm_localizations realm_localizations_pkey; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_localizations
    ADD CONSTRAINT realm_localizations_pkey PRIMARY KEY (realm_id, locale);


--
-- Name: resource_attribute res_attr_pk; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT res_attr_pk PRIMARY KEY (id);


--
-- Name: keycloak_group sibling_names; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT sibling_names UNIQUE (realm_id, parent_group, name);


--
-- Name: identity_provider uk_2daelwnibji49avxsrtuf6xj33; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT uk_2daelwnibji49avxsrtuf6xj33 UNIQUE (provider_alias, realm_id);


--
-- Name: client uk_b71cjlbenv945rb6gcon438at; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk_b71cjlbenv945rb6gcon438at UNIQUE (realm_id, client_id);


--
-- Name: client_scope uk_cli_scope; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT uk_cli_scope UNIQUE (realm_id, name);


--
-- Name: user_entity uk_dykn684sl8up1crfei6eckhd7; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_dykn684sl8up1crfei6eckhd7 UNIQUE (realm_id, email_constraint);


--
-- Name: resource_server_resource uk_frsr6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5ha6 UNIQUE (name, owner, resource_server_id);


--
-- Name: resource_server_perm_ticket uk_frsr6t700s9v50bu18ws5pmt; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5pmt UNIQUE (owner, requester, resource_server_id, resource_id, scope_id);


--
-- Name: resource_server_policy uk_frsrpt700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT uk_frsrpt700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: resource_server_scope uk_frsrst700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT uk_frsrst700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: user_consent uk_jkuwuvd56ontgsuhogm8uewrt; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_jkuwuvd56ontgsuhogm8uewrt UNIQUE (client_id, client_storage_provider, external_client_id, user_id);


--
-- Name: realm uk_orvsdmla56612eaefiq6wl5oi; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT uk_orvsdmla56612eaefiq6wl5oi UNIQUE (name);


--
-- Name: user_entity uk_ru8tt6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_ru8tt6t700s9v50bu18ws5ha6 UNIQUE (realm_id, username);


--
-- Name: idx_admin_event_time; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_admin_event_time ON public.admin_event_entity USING btree (realm_id, admin_event_time);


--
-- Name: idx_assoc_pol_assoc_pol_id; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_assoc_pol_assoc_pol_id ON public.associated_policy USING btree (associated_policy_id);


--
-- Name: idx_auth_config_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_auth_config_realm ON public.authenticator_config USING btree (realm_id);


--
-- Name: idx_auth_exec_flow; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_auth_exec_flow ON public.authentication_execution USING btree (flow_id);


--
-- Name: idx_auth_exec_realm_flow; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_auth_exec_realm_flow ON public.authentication_execution USING btree (realm_id, flow_id);


--
-- Name: idx_auth_flow_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_auth_flow_realm ON public.authentication_flow USING btree (realm_id);


--
-- Name: idx_cl_clscope; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_cl_clscope ON public.client_scope_client USING btree (scope_id);


--
-- Name: idx_client_id; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_client_id ON public.client USING btree (client_id);


--
-- Name: idx_client_init_acc_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_client_init_acc_realm ON public.client_initial_access USING btree (realm_id);


--
-- Name: idx_client_session_session; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_client_session_session ON public.client_session USING btree (session_id);


--
-- Name: idx_clscope_attrs; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_clscope_attrs ON public.client_scope_attributes USING btree (scope_id);


--
-- Name: idx_clscope_cl; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_clscope_cl ON public.client_scope_client USING btree (client_id);


--
-- Name: idx_clscope_protmap; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_clscope_protmap ON public.protocol_mapper USING btree (client_scope_id);


--
-- Name: idx_clscope_role; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_clscope_role ON public.client_scope_role_mapping USING btree (scope_id);


--
-- Name: idx_compo_config_compo; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_compo_config_compo ON public.component_config USING btree (component_id);


--
-- Name: idx_component_provider_type; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_component_provider_type ON public.component USING btree (provider_type);


--
-- Name: idx_component_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_component_realm ON public.component USING btree (realm_id);


--
-- Name: idx_composite; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_composite ON public.composite_role USING btree (composite);


--
-- Name: idx_composite_child; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_composite_child ON public.composite_role USING btree (child_role);


--
-- Name: idx_defcls_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_defcls_realm ON public.default_client_scope USING btree (realm_id);


--
-- Name: idx_defcls_scope; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_defcls_scope ON public.default_client_scope USING btree (scope_id);


--
-- Name: idx_event_time; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_event_time ON public.event_entity USING btree (realm_id, event_time);


--
-- Name: idx_fedidentity_feduser; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fedidentity_feduser ON public.federated_identity USING btree (federated_user_id);


--
-- Name: idx_fedidentity_user; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fedidentity_user ON public.federated_identity USING btree (user_id);


--
-- Name: idx_fu_attribute; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_attribute ON public.fed_user_attribute USING btree (user_id, realm_id, name);


--
-- Name: idx_fu_cnsnt_ext; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_cnsnt_ext ON public.fed_user_consent USING btree (user_id, client_storage_provider, external_client_id);


--
-- Name: idx_fu_consent; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_consent ON public.fed_user_consent USING btree (user_id, client_id);


--
-- Name: idx_fu_consent_ru; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_consent_ru ON public.fed_user_consent USING btree (realm_id, user_id);


--
-- Name: idx_fu_credential; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_credential ON public.fed_user_credential USING btree (user_id, type);


--
-- Name: idx_fu_credential_ru; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_credential_ru ON public.fed_user_credential USING btree (realm_id, user_id);


--
-- Name: idx_fu_group_membership; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_group_membership ON public.fed_user_group_membership USING btree (user_id, group_id);


--
-- Name: idx_fu_group_membership_ru; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_group_membership_ru ON public.fed_user_group_membership USING btree (realm_id, user_id);


--
-- Name: idx_fu_required_action; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_required_action ON public.fed_user_required_action USING btree (user_id, required_action);


--
-- Name: idx_fu_required_action_ru; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_required_action_ru ON public.fed_user_required_action USING btree (realm_id, user_id);


--
-- Name: idx_fu_role_mapping; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_role_mapping ON public.fed_user_role_mapping USING btree (user_id, role_id);


--
-- Name: idx_fu_role_mapping_ru; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_fu_role_mapping_ru ON public.fed_user_role_mapping USING btree (realm_id, user_id);


--
-- Name: idx_group_att_by_name_value; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_group_att_by_name_value ON public.group_attribute USING btree (name, ((value)::character varying(250)));


--
-- Name: idx_group_attr_group; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_group_attr_group ON public.group_attribute USING btree (group_id);


--
-- Name: idx_group_role_mapp_group; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_group_role_mapp_group ON public.group_role_mapping USING btree (group_id);


--
-- Name: idx_id_prov_mapp_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_id_prov_mapp_realm ON public.identity_provider_mapper USING btree (realm_id);


--
-- Name: idx_ident_prov_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_ident_prov_realm ON public.identity_provider USING btree (realm_id);


--
-- Name: idx_keycloak_role_client; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_keycloak_role_client ON public.keycloak_role USING btree (client);


--
-- Name: idx_keycloak_role_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_keycloak_role_realm ON public.keycloak_role USING btree (realm);


--
-- Name: idx_offline_css_preload; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_offline_css_preload ON public.offline_client_session USING btree (client_id, offline_flag);


--
-- Name: idx_offline_uss_by_user; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_offline_uss_by_user ON public.offline_user_session USING btree (user_id, realm_id, offline_flag);


--
-- Name: idx_offline_uss_by_usersess; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_offline_uss_by_usersess ON public.offline_user_session USING btree (realm_id, offline_flag, user_session_id);


--
-- Name: idx_offline_uss_createdon; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_offline_uss_createdon ON public.offline_user_session USING btree (created_on);


--
-- Name: idx_offline_uss_preload; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_offline_uss_preload ON public.offline_user_session USING btree (offline_flag, created_on, user_session_id);


--
-- Name: idx_protocol_mapper_client; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_protocol_mapper_client ON public.protocol_mapper USING btree (client_id);


--
-- Name: idx_realm_attr_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_realm_attr_realm ON public.realm_attribute USING btree (realm_id);


--
-- Name: idx_realm_clscope; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_realm_clscope ON public.client_scope USING btree (realm_id);


--
-- Name: idx_realm_def_grp_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_realm_def_grp_realm ON public.realm_default_groups USING btree (realm_id);


--
-- Name: idx_realm_evt_list_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_realm_evt_list_realm ON public.realm_events_listeners USING btree (realm_id);


--
-- Name: idx_realm_evt_types_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_realm_evt_types_realm ON public.realm_enabled_event_types USING btree (realm_id);


--
-- Name: idx_realm_master_adm_cli; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_realm_master_adm_cli ON public.realm USING btree (master_admin_client);


--
-- Name: idx_realm_supp_local_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_realm_supp_local_realm ON public.realm_supported_locales USING btree (realm_id);


--
-- Name: idx_redir_uri_client; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_redir_uri_client ON public.redirect_uris USING btree (client_id);


--
-- Name: idx_req_act_prov_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_req_act_prov_realm ON public.required_action_provider USING btree (realm_id);


--
-- Name: idx_res_policy_policy; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_res_policy_policy ON public.resource_policy USING btree (policy_id);


--
-- Name: idx_res_scope_scope; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_res_scope_scope ON public.resource_scope USING btree (scope_id);


--
-- Name: idx_res_serv_pol_res_serv; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_res_serv_pol_res_serv ON public.resource_server_policy USING btree (resource_server_id);


--
-- Name: idx_res_srv_res_res_srv; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_res_srv_res_res_srv ON public.resource_server_resource USING btree (resource_server_id);


--
-- Name: idx_res_srv_scope_res_srv; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_res_srv_scope_res_srv ON public.resource_server_scope USING btree (resource_server_id);


--
-- Name: idx_role_attribute; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_role_attribute ON public.role_attribute USING btree (role_id);


--
-- Name: idx_role_clscope; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_role_clscope ON public.client_scope_role_mapping USING btree (role_id);


--
-- Name: idx_scope_mapping_role; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_scope_mapping_role ON public.scope_mapping USING btree (role_id);


--
-- Name: idx_scope_policy_policy; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_scope_policy_policy ON public.scope_policy USING btree (policy_id);


--
-- Name: idx_update_time; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_update_time ON public.migration_model USING btree (update_time);


--
-- Name: idx_us_sess_id_on_cl_sess; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_us_sess_id_on_cl_sess ON public.offline_client_session USING btree (user_session_id);


--
-- Name: idx_usconsent_clscope; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_usconsent_clscope ON public.user_consent_client_scope USING btree (user_consent_id);


--
-- Name: idx_user_attribute; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_user_attribute ON public.user_attribute USING btree (user_id);


--
-- Name: idx_user_attribute_name; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_user_attribute_name ON public.user_attribute USING btree (name, value);


--
-- Name: idx_user_consent; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_user_consent ON public.user_consent USING btree (user_id);


--
-- Name: idx_user_credential; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_user_credential ON public.credential USING btree (user_id);


--
-- Name: idx_user_email; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_user_email ON public.user_entity USING btree (email);


--
-- Name: idx_user_group_mapping; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_user_group_mapping ON public.user_group_membership USING btree (user_id);


--
-- Name: idx_user_reqactions; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_user_reqactions ON public.user_required_action USING btree (user_id);


--
-- Name: idx_user_role_mapping; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_user_role_mapping ON public.user_role_mapping USING btree (user_id);


--
-- Name: idx_user_service_account; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_user_service_account ON public.user_entity USING btree (realm_id, service_account_client_link);


--
-- Name: idx_usr_fed_map_fed_prv; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_usr_fed_map_fed_prv ON public.user_federation_mapper USING btree (federation_provider_id);


--
-- Name: idx_usr_fed_map_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_usr_fed_map_realm ON public.user_federation_mapper USING btree (realm_id);


--
-- Name: idx_usr_fed_prv_realm; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_usr_fed_prv_realm ON public.user_federation_provider USING btree (realm_id);


--
-- Name: idx_web_orig_client; Type: INDEX; Schema: public; Owner: dev_user
--

CREATE INDEX idx_web_orig_client ON public.web_origins USING btree (client_id);


--
-- Name: client_session_auth_status auth_status_constraint; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_session_auth_status
    ADD CONSTRAINT auth_status_constraint FOREIGN KEY (client_session) REFERENCES public.client_session(id);


--
-- Name: identity_provider fk2b4ebc52ae5c3b34; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT fk2b4ebc52ae5c3b34 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: client_attributes fk3c47c64beacca966; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT fk3c47c64beacca966 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: federated_identity fk404288b92ef007a6; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT fk404288b92ef007a6 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_node_registrations fk4129723ba992f594; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT fk4129723ba992f594 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: client_session_note fk5edfb00ff51c2736; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_session_note
    ADD CONSTRAINT fk5edfb00ff51c2736 FOREIGN KEY (client_session) REFERENCES public.client_session(id);


--
-- Name: user_session_note fk5edfb00ff51d3472; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_session_note
    ADD CONSTRAINT fk5edfb00ff51d3472 FOREIGN KEY (user_session) REFERENCES public.user_session(id);


--
-- Name: client_session_role fk_11b7sgqw18i532811v7o2dv76; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_session_role
    ADD CONSTRAINT fk_11b7sgqw18i532811v7o2dv76 FOREIGN KEY (client_session) REFERENCES public.client_session(id);


--
-- Name: redirect_uris fk_1burs8pb4ouj97h5wuppahv9f; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT fk_1burs8pb4ouj97h5wuppahv9f FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: user_federation_provider fk_1fj32f6ptolw2qy60cd8n01e8; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT fk_1fj32f6ptolw2qy60cd8n01e8 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: client_session_prot_mapper fk_33a8sgqw18i532811v7o2dk89; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_session_prot_mapper
    ADD CONSTRAINT fk_33a8sgqw18i532811v7o2dk89 FOREIGN KEY (client_session) REFERENCES public.client_session(id);


--
-- Name: realm_required_credential fk_5hg65lybevavkqfki3kponh9v; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT fk_5hg65lybevavkqfki3kponh9v FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_attribute fk_5hrm2vlf9ql5fu022kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu022kqepovbr FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: user_attribute fk_5hrm2vlf9ql5fu043kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu043kqepovbr FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: user_required_action fk_6qj3w1jw9cvafhe19bwsiuvmd; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT fk_6qj3w1jw9cvafhe19bwsiuvmd FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: keycloak_role fk_6vyqfe4cn4wlq8r6kt5vdsj5c; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT fk_6vyqfe4cn4wlq8r6kt5vdsj5c FOREIGN KEY (realm) REFERENCES public.realm(id);


--
-- Name: realm_smtp_config fk_70ej8xdxgxd0b9hh6180irr0o; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT fk_70ej8xdxgxd0b9hh6180irr0o FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_attribute fk_8shxd6l3e9atqukacxgpffptw; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT fk_8shxd6l3e9atqukacxgpffptw FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: composite_role fk_a63wvekftu8jo1pnj81e7mce2; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_a63wvekftu8jo1pnj81e7mce2 FOREIGN KEY (composite) REFERENCES public.keycloak_role(id);


--
-- Name: authentication_execution fk_auth_exec_flow; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_flow FOREIGN KEY (flow_id) REFERENCES public.authentication_flow(id);


--
-- Name: authentication_execution fk_auth_exec_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authentication_flow fk_auth_flow_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT fk_auth_flow_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authenticator_config fk_auth_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT fk_auth_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: client_session fk_b4ao2vcvat6ukau74wbwtfqo1; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_session
    ADD CONSTRAINT fk_b4ao2vcvat6ukau74wbwtfqo1 FOREIGN KEY (session_id) REFERENCES public.user_session(id);


--
-- Name: user_role_mapping fk_c4fqv34p1mbylloxang7b1q3l; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT fk_c4fqv34p1mbylloxang7b1q3l FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_scope_attributes fk_cl_scope_attr_scope; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT fk_cl_scope_attr_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_scope_role_mapping fk_cl_scope_rm_scope; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT fk_cl_scope_rm_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_user_session_note fk_cl_usr_ses_note; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_user_session_note
    ADD CONSTRAINT fk_cl_usr_ses_note FOREIGN KEY (client_session) REFERENCES public.client_session(id);


--
-- Name: protocol_mapper fk_cli_scope_mapper; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_cli_scope_mapper FOREIGN KEY (client_scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_initial_access fk_client_init_acc_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT fk_client_init_acc_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: component_config fk_component_config; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT fk_component_config FOREIGN KEY (component_id) REFERENCES public.component(id);


--
-- Name: component fk_component_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT fk_component_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_default_groups fk_def_groups_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT fk_def_groups_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_mapper_config fk_fedmapper_cfg; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT fk_fedmapper_cfg FOREIGN KEY (user_federation_mapper_id) REFERENCES public.user_federation_mapper(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_fedprv; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_fedprv FOREIGN KEY (federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: associated_policy fk_frsr5s213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsr5s213xcx4wnkog82ssrfy FOREIGN KEY (associated_policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrasp13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrasp13xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog82sspmt; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82sspmt FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_resource fk_frsrho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog83sspmt; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog83sspmt FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog84sspmt; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog84sspmt FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: associated_policy fk_frsrpas14xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsrpas14xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrpass3xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrpass3xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_perm_ticket fk_frsrpo2128cx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrpo2128cx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_policy fk_frsrpo213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT fk_frsrpo213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_scope fk_frsrpos13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrpos13xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpos53xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpos53xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpp213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpp213xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_scope fk_frsrps213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrps213xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_scope fk_frsrso213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT fk_frsrso213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: composite_role fk_gr7thllb9lu8q4vqa4524jjy8; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_gr7thllb9lu8q4vqa4524jjy8 FOREIGN KEY (child_role) REFERENCES public.keycloak_role(id);


--
-- Name: user_consent_client_scope fk_grntcsnt_clsc_usc; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT fk_grntcsnt_clsc_usc FOREIGN KEY (user_consent_id) REFERENCES public.user_consent(id);


--
-- Name: user_consent fk_grntcsnt_user; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT fk_grntcsnt_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: group_attribute fk_group_attribute_group; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT fk_group_attribute_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: group_role_mapping fk_group_role_group; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT fk_group_role_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: realm_enabled_event_types fk_h846o4h0w8epx5nwedrf5y69j; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT fk_h846o4h0w8epx5nwedrf5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_events_listeners fk_h846o4h0w8epx5nxev9f5y69j; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT fk_h846o4h0w8epx5nxev9f5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: identity_provider_mapper fk_idpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT fk_idpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: idp_mapper_config fk_idpmconfig; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT fk_idpmconfig FOREIGN KEY (idp_mapper_id) REFERENCES public.identity_provider_mapper(id);


--
-- Name: web_origins fk_lojpho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT fk_lojpho213xcx4wnkog82ssrfy FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: scope_mapping fk_ouse064plmlr732lxjcn1q5f1; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT fk_ouse064plmlr732lxjcn1q5f1 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: protocol_mapper fk_pcm_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_pcm_realm FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: credential fk_pfyr0glasqyl0dei3kl69r6v0; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT fk_pfyr0glasqyl0dei3kl69r6v0 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: protocol_mapper_config fk_pmconfig; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT fk_pmconfig FOREIGN KEY (protocol_mapper_id) REFERENCES public.protocol_mapper(id);


--
-- Name: default_client_scope fk_r_def_cli_scope_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT fk_r_def_cli_scope_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: required_action_provider fk_req_act_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT fk_req_act_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_uris fk_resource_server_uris; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT fk_resource_server_uris FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: role_attribute fk_role_attribute_id; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT fk_role_attribute_id FOREIGN KEY (role_id) REFERENCES public.keycloak_role(id);


--
-- Name: realm_supported_locales fk_supported_locales_realm; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT fk_supported_locales_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_config fk_t13hpu1j94r2ebpekr39x5eu5; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT fk_t13hpu1j94r2ebpekr39x5eu5 FOREIGN KEY (user_federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_group_membership fk_user_group_user; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT fk_user_group_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: policy_config fkdc34197cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT fkdc34197cf864c4e43 FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: identity_provider_config fkdc4897cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: dev_user
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT fkdc4897cf864c4e43 FOREIGN KEY (identity_provider_id) REFERENCES public.identity_provider(internal_id);


--
-- PostgreSQL database dump complete
--

