import React from "react";
import TextBlock from "./TextBlock";

function PrivacyPolicy(){
    return(
        <div className="policy-text">
            <TextBlock
                title="Privacy Policy – GenIz"
                textList={[
                    {subtitle: "1. Overview", content: `GenIz is an open-source desktop application designed to generate quizzes from user-provided documents.

                        The application is designed with a local-first architecture, meaning that all data processing and storage occur on the user's device unless explicitly configured otherwise by the user.

                        The developer of GenIz does not operate any central servers and does not collect or store user data.`},
                    {subtitle: "2. Data Collection", content: `GenIz does not collect, transmit, or store personal data on external servers.

                        All data processed by the application remains on the user's local machine. This may include:
                        • Documents uploaded by the user (e.g., PDF files)
                        • Generated quizzes and notes
                        • Application configuration files
                        • API keys provided by the user
                        • Local database content

                        The developer has no access to any of this data.`},
                    {subtitle: "3. Local Data Storage", content: `All data used by GenIz is stored locally on the user's device and is not encrypted by default.

                        By using the application, you acknowledge that:
                        • Data stored locally may be accessible to other software on your system
                        • You are responsible for securing your device and environment`},
                    {subtitle: "4. Use of Third-Party AI Providers (Optional)", content: `GenIz allows users to optionally configure and use third-party AI providers, such as:
                        • OpenAI
                        • Google (Gemini)

                        When using these services:
                        • Data may be transmitted directly from your device to the selected provider
                        • The developer of GenIz does not intercept, access, or store this data
                        • Your use of these services is governed by their respective privacy policies and terms

                        You are solely responsible for understanding how your data is handled by these providers.`},
                    {subtitle: "5. API Keys", content: `Users may provide their own API keys to access third-party services.
                        • API keys are stored locally (e.g., in .env files or local configuration)
                        • They are never transmitted to the developer
                        • The developer is not responsible for misuse, leakage, or unauthorized access to these keys

                        Users are strongly encouraged to:
                        • Keep API keys confidential
                        • Use usage limits or billing controls where available
                        • Revoke compromised keys immediately`},
                    {subtitle: "6. Authentication (Google OAuth)", content: `GenIz may support authentication via Google OAuth2.

                        When using this feature:
                        • A unique user identifier (Google User ID) may be retrieved
                        • This identifier is used only locally to distinguish users within the application
                        • No authentication data is transmitted to or stored on developer-controlled servers.

                        GenIz does not access or store:
                        • Passwords
                        • Full Google account data
                        • Any data beyond what is strictly necessary for identification

                        Use of Google authentication is subject to Google's own privacy policy.`},
                    {subtitle: "7. No Remote Services", content: `GenIz does not provide:
                        • Cloud synchronization
                        • Remote storage
                        • Analytics tracking
                        • Telemetry.

                        No data is sent to the developer at any time.`},
                    {subtitle: "8. Open Source Transparency", content: `GenIz is open-source software.

                        Users are encouraged to:
                        • Review the source code
                        • Verify how data is handled
                        • Modify the software according to their needs.

                        The developer makes no claims beyond what is visible in the source code.`},
                    {subtitle: "9. User Responsibility", content: `By using GenIz, you acknowledge that you are solely responsible for:
                        • The data you input into the application
                        • The services you connect to
                        • The security of your local environment
                        • Compliance with applicable laws and regulations.`},
                    {subtitle: "10. Security Disclaimer", content: `While reasonable care has been taken in designing the application:
                        • No software can guarantee absolute security.
                        The developer is not responsible for data breaches caused by:
                        • Malware
                        • System compromise
                        • Misconfiguration
                        • Third-party services.`},
                    {subtitle: "11. Legal Basis (GDPR Context)", content: `GenIz does not process personal data on behalf of the developer.

                        If any personal data is processed, it is:
                        • Processed locally by the user
                        • Under the full control of the user.

                        Therefore, the user acts as the data controller for any personal data processed by the application.`},
                    {subtitle: "12. Changes to This Policy", content: `This privacy policy may be updated from time to time.

                        Users are encouraged to review it periodically.`},
                    {subtitle: "13. Contact", content: `For questions, issues, or concerns, please use the official GitHub repository of the project.`}
                ]}
            />
        </div>
    );
}

export default PrivacyPolicy;